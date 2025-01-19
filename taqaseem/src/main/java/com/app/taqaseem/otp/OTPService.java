package com.app.taqaseem.otp;

import com.app.taqaseem.jwt.JWTService;
import com.app.taqaseem.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.app.taqaseem.constant.Messages.*;

@Service
@RequiredArgsConstructor
public class OTPService {
  private final RedisUtil redisUtil;
  private final OTPProvider otpProvider;
  private final JWTService jwtService;

  public void generateOTP(OTPGenerateRequestDTO otpGenerateRequestDTO) {
    String otp = otpProvider.sendOTP(otpGenerateRequestDTO.getPhoneNumber());
    redisUtil.saveOTP(otp, otpGenerateRequestDTO.getPhoneNumber());
  }

  public OTPAuthenticateResponseDTO authenticateOTP(
      OTPAuthenticateRequestDTO otpAuthenticateRequestDTO) {
    boolean isAuthenticated =
        redisUtil.isPhoneAuthenticated(
            otpAuthenticateRequestDTO.getOtp(), otpAuthenticateRequestDTO.getPhoneNumber());

    if (isAuthenticated) {
      return OTPAuthenticateResponseDTO.builder()
              .isAuthenticated(true)
              .messageEN(SUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_EN)
              .messageAR(SUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_AR)
              .jwt(jwtService.generateNewAccessAndRefreshTokenForUser(
                      otpAuthenticateRequestDTO.getPhoneNumber()))
              .build();
    }
    return OTPAuthenticateResponseDTO.builder()
            .isAuthenticated(false)
            .messageEN(UNSUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_EN)
            .messageAR(UNSUCCESSFUL_OTP_AUTHENTICATION_MESSAGE_AR)
            .jwt(null)
            .build();
  }
}
