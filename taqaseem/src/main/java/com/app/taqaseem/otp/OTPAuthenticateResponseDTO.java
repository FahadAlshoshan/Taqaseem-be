package com.app.taqaseem.otp;

import com.app.taqaseem.jwt.JWTDTO;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OTPAuthenticateResponseDTO implements Serializable {
  private boolean isAuthenticated;
  private String message;
  private String lang;
  private JWTDTO jwt;
}
