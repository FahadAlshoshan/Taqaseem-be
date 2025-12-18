package com.app.taqaseem.security.jwt.impl;

import com.app.taqaseem.exception.InvalidJwtErrorException;
import com.app.taqaseem.model.UserInfo;
import com.app.taqaseem.repository.UserRepository;
import com.app.taqaseem.security.jwt.AuthenticationService;
import com.app.taqaseem.security.jwt.JWTDTO;
import com.app.taqaseem.security.jwt.TokenService;
import com.app.taqaseem.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("!local-clerk")
@RequiredArgsConstructor
public class CustomAuthenticationService implements AuthenticationService {
  private final TokenService tokenService;
  private final UserRepository userRepository;
  private final RedisUtil redisUtil;

  @Override
  public JWTDTO refreshAccessToken(JWTDTO currentJwt) {
    final String phoneNumber = tokenService.extractUserIdentifier(currentJwt.getRefreshToken());
    if (phoneNumber == null) {
      log.error("Invalid refresh token, phone number is null");
      throw new InvalidJwtErrorException("Invalid refresh token, phone number is null");
    }

    UserInfo user =
        userRepository
            .findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new UsernameNotFoundException("Phone number not found"));

    if (tokenService.isTokenInvalid(currentJwt.getRefreshToken(), user)) {
      log.error("Invalid refresh token");
      throw new InvalidJwtErrorException("Invalid refresh token");
    }

    JWTDTO newJWTDTO =
        JWTDTO
            .builder()
            .accessToken(tokenService.generateAccessToken(user))
            .refreshToken(currentJwt.getRefreshToken())
            .build();

    redisUtil.saveActiveAccessToken(newJWTDTO.getAccessToken(), phoneNumber);
    return newJWTDTO;
  }

  @Override
  public JWTDTO generateNewTokensForUser(UserInfo user) {
    JWTDTO newJWTDTO =
        JWTDTO
            .builder()
            .accessToken(tokenService.generateAccessToken(user))
            .refreshToken(tokenService.generateRefreshToken(user))
            .build();

    redisUtil.saveActiveAccessToken(newJWTDTO.getAccessToken(), user.getPhoneNumber());
    return newJWTDTO;
  }
}
