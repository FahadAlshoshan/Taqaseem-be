package com.app.taqaseem.exception;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  @ExceptionHandler({
    JwtException.class,
    ExpiredJwtException.class,
    UnsupportedJwtException.class,
    MalformedJwtException.class,
    SignatureException.class,
    UsernameNotFoundException.class,
    InvalidJwtErrorException.class
  })
  public ResponseEntity<?> handleException(Exception e) {
    return ResponseEntity.status(UNAUTHORIZED)
        .body("""
                 {"message": "%s"}
              """.formatted(e.getMessage()));
  }

  @ExceptionHandler(CustomRedisException.class)
  public ResponseEntity<?> handleRedisException(CustomRedisException e) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .body("""
                 {"message": "%s"}
              """.formatted(e.getMessage()));
  }
}
