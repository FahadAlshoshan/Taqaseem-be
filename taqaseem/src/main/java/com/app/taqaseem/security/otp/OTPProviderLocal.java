package com.app.taqaseem.security.otp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OTPProviderLocal implements OTPProvider {

  @Override
  public String sendOTP(String phoneNumber) {
    return "123456";
  }
}
