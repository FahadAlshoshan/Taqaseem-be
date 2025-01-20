package com.app.taqaseem.otp;

import com.app.taqaseem.jwt.JWTService;
import com.app.taqaseem.model.UserInfo;
import com.app.taqaseem.repository.UserRepository;
import com.app.taqaseem.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.app.taqaseem.constant.Messages.*;

@Service
@RequiredArgsConstructor
public class OTPService {
  private final RedisUtil redisUtil;
  private final OTPProvider otpProvider;
  private final JWTService jwtService;
  private final UserRepository userRepository;

  public void generateOTP(OTPGenerateRequestDTO otpGenerateRequestDTO) {
    String otp = otpProvider.sendOTP(otpGenerateRequestDTO.getPhoneNumber());
    redisUtil.saveOTP(otp, otpGenerateRequestDTO.getPhoneNumber());
  }

    @Transactional
    public OTPAuthenticateResponseDTO authenticateOTP(OTPAuthenticateRequestDTO otpAuthenticateRequestDTO) {
        boolean isAuthenticated = redisUtil.isPhoneAuthenticated(otpAuthenticateRequestDTO.getOtp(), otpAuthenticateRequestDTO.getPhoneNumber());

        if (isAuthenticated) {
            String userPhoneNumber = otpAuthenticateRequestDTO.getPhoneNumber();
            AtomicReference<StatusEnum> status = new AtomicReference<>(StatusEnum.SUCCESS);

            UserInfo userInfo = userRepository.findByPhoneNumber(userPhoneNumber).orElseGet(() -> {
                try {
                    UserInfo savedUser = userRepository.save(UserInfo.builder().phoneNumber(userPhoneNumber).build());
                    status.set(StatusEnum.FIRST_TIME);
                    return savedUser;
                } catch (DataIntegrityViolationException e) {
                    return userRepository.findByPhoneNumber(userPhoneNumber).orElseThrow(() -> new IllegalStateException("Unexpected state"));
                }
            });

            return OTPAuthenticateResponseDTO.builder().isAuthenticated(true).status(status.get().value).messageEN(SUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_EN).messageAR(SUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_AR).jwt(jwtService.generateNewAccessAndRefreshTokenForUser(userInfo)).build();
        }
        return OTPAuthenticateResponseDTO.builder().isAuthenticated(false).status(StatusEnum.FAILURE.value).messageEN(UNSUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_EN).messageAR(UNSUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_AR).jwt(null).build();
    }
}
