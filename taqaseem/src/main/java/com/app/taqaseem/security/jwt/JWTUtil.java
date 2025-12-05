package com.app.taqaseem.security.jwt;

import com.app.taqaseem.exception.InvalidJwtErrorException;
import com.app.taqaseem.security.TaqaseemUserDetails;
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

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class JWTUtil {
  private final int MS_IN_MINUTE = 60000;

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  public String extractPhoneNumber(String token) {
    try {
      return extractClaim(token, Claims::getSubject);
    } catch (MalformedJwtException e){
        throw new InvalidJwtErrorException("Malformed JWT token");
    }
    catch (Exception e) {
      log.error("Error extracting phone number", e);
      throw new InvalidJwtErrorException(e.getMessage());
    }
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateAccessToken(TaqaseemUserDetails userDetails) {
    return generateAccessToken(new HashMap<>(), userDetails);
  }

  public String generateAccessToken(Map<String, Object> extraClaims, TaqaseemUserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

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

  public boolean isTokenValid(String token, TaqaseemUserDetails userDetails) {
    final String phoneNumber = extractPhoneNumber(token);
    return (phoneNumber.equals(userDetails.getPhoneNumber())) && !isTokenExpired(token);
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
