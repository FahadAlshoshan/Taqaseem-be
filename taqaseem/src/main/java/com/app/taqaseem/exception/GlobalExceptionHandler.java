package com.app.taqaseem.exception;

import com.app.taqaseem.dto.ChangeNameResponseDTO;
import com.app.taqaseem.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

import static com.app.taqaseem.constant.Messages.UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR;
import static com.app.taqaseem.constant.Messages.UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomRedisException.class)
  public ResponseEntity<ErrorResponse> handleRedisException(CustomRedisException e) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(INTERNAL_SERVER_ERROR, e.getMessage()));
  }
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
    return ResponseEntity.status(UNAUTHORIZED)
            .body(ErrorResponse.of(UNAUTHORIZED, e.getMessage()));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ChangeNameResponseDTO> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ChangeNameResponseDTO.builder().messageAR(UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR).messageEN(UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN).build());
  }

  @ExceptionHandler(InvalidJwtErrorException.class)
  public ResponseEntity<ErrorResponse> handleInvalidJwtErrorException(InvalidJwtErrorException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse.of(HttpStatus.UNAUTHORIZED, ex.getMessage()));
  }

  @ExceptionHandler(UserAuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleUserAuthenticationException(UserAuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse.of(HttpStatus.UNAUTHORIZED, ex.getMessage()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
    String message = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));
    
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, message));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
    
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, message));
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UserAlreadyExistsException ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ErrorResponse.of(HttpStatus.UNAUTHORIZED, ex.getMessage()));
  }
}
