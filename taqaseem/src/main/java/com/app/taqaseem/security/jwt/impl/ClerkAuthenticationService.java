package com.app.taqaseem.security.jwt.impl;

import com.app.taqaseem.model.UserInfo;
import com.app.taqaseem.security.jwt.AuthenticationService;
import com.app.taqaseem.security.jwt.JWTDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"local-clerk"})
@Slf4j
public class ClerkAuthenticationService implements AuthenticationService {

  @Override
  public JWTDTO generateNewTokensForUser(UserInfo user) {
    throw new UnsupportedOperationException("Token generation is handled by Clerk on the frontend");
  }

  @Override
  public JWTDTO refreshAccessToken(JWTDTO currentJwt) {
    throw new UnsupportedOperationException("Token refresh is handled by Clerk on the frontend");
  }
}
