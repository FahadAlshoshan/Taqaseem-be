package com.app.taqaseem.security.otp;

import com.app.taqaseem.security.jwt.JWTDTO;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OTPAuthenticateResponseDTO implements Serializable {
  private boolean isRegistered;
  private boolean isAuthenticated;
  private String messageEN;
  private String messageAR;
  private JWTDTO jwt;
}

