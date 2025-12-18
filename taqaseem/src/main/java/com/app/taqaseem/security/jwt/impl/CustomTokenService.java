package com.app.taqaseem.security.jwt.impl;

import com.app.taqaseem.exception.InvalidJwtErrorException;
import com.app.taqaseem.security.TaqaseemUserDetailService;
import com.app.taqaseem.security.TaqaseemUserDetails;
import com.app.taqaseem.security.jwt.TokenService;
import com.app.taqaseem.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@Profile("!local-clerk")
public class CustomTokenService implements TokenService {
  private final int MS_IN_MINUTE = 60000;
  private final TaqaseemUserDetailService userDetailsService;
  private final RedisUtil redisUtil;

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  @Override
  public String extractUserIdentifier(String token) {
    try {
      return extractClaim(token, Claims::getSubject);
    } catch (MalformedJwtException e) {
      throw new InvalidJwtErrorException("Malformed JWT token");
    } catch (Exception e) {
      log.error("Error extracting phone number", e);
      throw new InvalidJwtErrorException(e.getMessage());
    }
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public String generateAccessToken(TaqaseemUserDetails userDetails) {
    return generateAccessToken(new HashMap<>(), userDetails);
  }

  public String generateAccessToken(
      Map<String, Object> extraClaims, TaqaseemUserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  @Override
  public String generateRefreshToken(TaqaseemUserDetails userDetails) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  private String buildToken(
      Map<String, Object> extraClaims, TaqaseemUserDetails userDetails, long expiration) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getPhoneNumber())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + (expiration * MS_IN_MINUTE)))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public boolean isTokenInvalid(String token, TaqaseemUserDetails userDetails) {
    final String phoneNumber = extractUserIdentifier(token);
    return (!phoneNumber.equals(userDetails.getPhoneNumber())) || isTokenExpired(token);
  }

  @Override
  public boolean isTokenInactive(String token, String phoneNumber) {
    return !redisUtil.isActiveAccessToken(token, phoneNumber);
  }

  @Override
  public TaqaseemUserDetails getUserDetailsFromToken(String token) {
    String phoneNumber = extractUserIdentifier(token);
    return userDetailsService.loadUserByPhoneNumber(phoneNumber);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
