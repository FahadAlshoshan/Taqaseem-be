package com.app.taqaseem.jwt;

import com.app.taqaseem.exception.InvalidJwtErrorException;
import com.app.taqaseem.model.UserInfo;
import com.app.taqaseem.repository.UserRepository;
import com.app.taqaseem.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JWTService {
  private final JWTUtil jwtUtil;
  private final UserRepository userRepository;
  private final RedisUtil redisUtil;

  public JWTDTO generateAccessTokenAndInvalidatePrevious(JWTDTO currentJwt) {
    final String phoneNumber = jwtUtil.extractPhoneNumber(currentJwt.getRefreshToken());
    if (phoneNumber == null) {
      log.error("Invalid refresh token, phone number is null");
      throw new InvalidJwtErrorException("Invalid refresh token, phone number is null");
    }

    UserInfo user =
        userRepository
            .findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new UsernameNotFoundException("Phone number not found"));

    if (!jwtUtil.isTokenValid(currentJwt.getRefreshToken(), user)) {
      log.error("Invalid refresh token");
      throw new InvalidJwtErrorException("Invalid refresh token");
    }

    JWTDTO newJWTDTO =
        JWTDTO
            .builder()
            .accessToken(jwtUtil.generateAccessToken(user))
            .refreshToken(currentJwt.getRefreshToken())
            .build();

    redisUtil.saveActiveAccessToken(newJWTDTO.getAccessToken(), phoneNumber);

    return newJWTDTO;
  }

  public JWTDTO generateNewAccessAndRefreshTokenForUser(String phoneNumber) {
    UserInfo userInfo =
        userRepository
            .findByPhoneNumber(phoneNumber)
            .orElse(userRepository.save(UserInfo.builder().phoneNumber(phoneNumber).build()));
    return JWTDTO
        .builder()
        .accessToken(jwtUtil.generateAccessToken(userInfo))
        .refreshToken(jwtUtil.generateRefreshToken(userInfo))
        .build();
  }
}
