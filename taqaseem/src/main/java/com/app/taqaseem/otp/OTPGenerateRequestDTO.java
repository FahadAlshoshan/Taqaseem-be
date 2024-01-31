package com.app.taqaseem.otp;

import com.app.taqaseem.validation.isValidPhoneNumber;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OTPGenerateRequestDTO implements Serializable {
  @isValidPhoneNumber private String phoneNumber;
}
