package com.app.taqaseem.otp;

import com.app.taqaseem.validation.isValidPhoneNumber;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OTPAuthenticateRequestDTO implements Serializable {
  @isValidPhoneNumber
  private String phoneNumber;
  @Pattern(regexp = "\\d{4}")
  private String otp;
}
