package com.app.taqaseem.util;

import static com.app.taqaseem.config.RedisConfig.ACTIVE_TOKENS_CACHE_NAME;
import static com.app.taqaseem.config.RedisConfig.OTP_CACHE_NAME;

import com.app.taqaseem.exception.CustomRedisException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisUtil {
  private final CacheManager cacheManager;
  private final RedisTemplate<String, String> redisTemplate;

  public RedisUtil(CacheManager cacheManager, RedisTemplate<String, String> redisTemplate) {
    this.cacheManager = cacheManager;
    this.redisTemplate = redisTemplate;
  }

  public boolean isActiveAccessToken(String accessToken, String phoneNumber) {
    try {
      return Objects.equals(
          Objects.requireNonNull(cacheManager.getCache(ACTIVE_TOKENS_CACHE_NAME))
              .get(phoneNumber, String.class),
          accessToken);
    } catch (Exception e) {
      log.error("Error while fetching from cache, ", e);
      throw new CustomRedisException(e.getMessage());
    }
  }

  public void saveActiveAccessToken(String accessToken, String phoneNumber) {
    try {
      Objects.requireNonNull(cacheManager.getCache(ACTIVE_TOKENS_CACHE_NAME))
          .put(phoneNumber, accessToken);
    } catch (Exception e) {
      log.error("Exception occurred while saving in cache, ", e);
      throw new CustomRedisException(e.getMessage());
    }
  }

  public void saveOTP(String otp, String phoneNumber) {
    try {
      Objects.requireNonNull(cacheManager.getCache(OTP_CACHE_NAME)).put(phoneNumber, otp);
    } catch (Exception e) {
      log.error("Exception occurred while saving in cache, ", e);
      throw new CustomRedisException(e.getMessage());
    }
  }
  public boolean isPhoneAuthenticated(String otp, String phoneNumber) {
    try {
      return Objects.equals(
          Objects.requireNonNull(cacheManager.getCache(OTP_CACHE_NAME))
              .get(phoneNumber, String.class),
          otp);
    } catch (Exception e) {
      log.error("Error while fetching from cache, ", e);
      throw new CustomRedisException(e.getMessage());
    }
  }
}
