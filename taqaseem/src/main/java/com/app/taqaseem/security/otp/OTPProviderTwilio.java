package com.app.taqaseem.security.otp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OTPProviderTwilio implements OTPProvider {

  @Override
  public String sendOTP(String phoneNumber) {
    //TODO: implement Twilio logic
    return "";
  }
}
