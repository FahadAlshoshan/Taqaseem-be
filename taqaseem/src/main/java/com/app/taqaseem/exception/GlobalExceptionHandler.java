package com.app.taqaseem.exception;

import com.app.taqaseem.dto.ApiResponse;
import com.app.taqaseem.dto.ChangeNameResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

import static com.app.taqaseem.constant.Messages.UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR;
import static com.app.taqaseem.constant.Messages.UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomRedisException.class)
  public ResponseEntity<?> handleRedisException(CustomRedisException e) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Map.of(
            "timestamp", LocalDateTime.now(),
            "status", INTERNAL_SERVER_ERROR.value(),
            "message", e.getMessage()
    ));

  }
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
    return ResponseEntity.status(UNAUTHORIZED).body(Map.of(
            "timestamp", LocalDateTime.now(),
            "status", UNAUTHORIZED.value(),
            "message", e.getMessage()
    ));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ChangeNameResponseDTO> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ChangeNameResponseDTO.builder().messageAR(UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR).messageEN(UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN).build());
  }

  @ExceptionHandler(InvalidJwtErrorException.class)
  public ResponseEntity<?> handleInvalidJwtErrorException(InvalidJwtErrorException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
            "timestamp", LocalDateTime.now(),
            "status", HttpStatus.UNAUTHORIZED.value(),
            "message", ex.getMessage()
    ));
  }

}
