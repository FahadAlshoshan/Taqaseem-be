package com.app.taqaseem.security.jwt;

import com.app.taqaseem.model.UserInfo;

public interface AuthenticationService {
  JWTDTO generateNewTokensForUser(UserInfo user);

  JWTDTO refreshAccessToken(JWTDTO currentJwt);
}
