package com.app.taqaseem.otp;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/otp")
public class OTPController {

  private final OTPService otpService;

  @PostMapping("/generate")
  public void generateOTP(@Valid @RequestBody OTPGenerateRequestDTO otpGenerateRequestDTO) {
    otpService.generateOTP(otpGenerateRequestDTO);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> checkOTP(
      @Valid @RequestBody OTPAuthenticateRequestDTO otpAuthenticateRequestDTO) {
    OTPAuthenticateResponseDTO otpAuthenticateResponseDTO =
        otpService.authenticateOTP(otpAuthenticateRequestDTO);

    return new ResponseEntity<>(
        otpAuthenticateResponseDTO,
        otpAuthenticateResponseDTO.isAuthenticated() ? OK : UNAUTHORIZED);
  }
}