package com.app.taqaseem.exception;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import io.jsonwebtoken.JwtException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
  @ExceptionHandler(InvalidJwtErrorException.class)
  public ResponseEntity<?> handleJwtErrorException(InvalidJwtErrorException e) {
    return ResponseEntity.status(UNAUTHORIZED).body(e.getMessage());
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
    return ResponseEntity.status(UNAUTHORIZED).body(e.getMessage());
  }

  @ExceptionHandler(CustomRedisException.class)
  public ResponseEntity<?> handleCustomRedisException(CustomRedisException e) {
    return ResponseEntity.status(UNAUTHORIZED).body(e.getMessage());
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<?> handleJwtException(JwtException e) {
    return ResponseEntity.status(UNAUTHORIZED).body(e.getMessage());
  }
}
