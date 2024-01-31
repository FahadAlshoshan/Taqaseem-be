package com.app.taqaseem.otp;

import com.app.taqaseem.jwt.JWTService;
import com.app.taqaseem.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    return OTPAuthenticateResponseDTO.builder()
        .isAuthenticated(isAuthenticated)
        .lang("EN")
        .message(isAuthenticated ? "Successfully authenticated!" : "Authentication unsuccessful!")
        .jwt(
            isAuthenticated
                ? jwtService.generateNewAccessAndRefreshTokenForUser(
                    otpAuthenticateRequestDTO.getPhoneNumber())
                : null)
        .build();
  }
}
