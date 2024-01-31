package com.app.taqaseem.otp;

import com.app.taqaseem.util.RedisUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OTPProviderLocal implements OTPProvider {

  @Override
  public String sendOTP(String phoneNumber) {
    return "1111";
  }
}
