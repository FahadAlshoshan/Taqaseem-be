package com.app.taqaseem.security.otp;

public interface OTPProvider {
  String sendOTP(String phoneNumber);
}
