package com.app.taqaseem.otp;

import com.app.taqaseem.util.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(enforceUniqueMethods = false)
public class OTPConfig {

  @Bean("otpProvider")
  @Profile({"local", "dev"})
  public OTPProvider otpProviderLocal() {
    return new OTPProviderLocal();
  }

  @Bean("otpProvider")
  @Profile("prod")
  public OTPProvider otpProviderProd() {
    return new OTPProviderTwilio();
  }
}
