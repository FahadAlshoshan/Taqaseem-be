package com.app.taqaseem.security.jwt;

import com.app.taqaseem.security.TaqaseemUserDetails;

public interface TokenService {
  String extractUserIdentifier(String token);

  boolean isTokenInvalid(String token, TaqaseemUserDetails userDetails);

  boolean isTokenInactive(String token, String userIdentifier);

  TaqaseemUserDetails getUserDetailsFromToken(String token);

  String generateAccessToken(TaqaseemUserDetails userDetails);

  String generateRefreshToken(TaqaseemUserDetails userDetails);
}
