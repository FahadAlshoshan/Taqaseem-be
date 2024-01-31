package com.app.taqaseem.jwt;

import static org.springframework.http.HttpStatus.OK;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/jwt")
public class JWTController {
  private final JWTService jwtService;

  @PostMapping("/refresh")
  public ResponseEntity<?> refreshToken(@Valid @RequestBody JWTDTO jwtDTO) {
    return new ResponseEntity<>(jwtService.generateAccessTokenAndInvalidatePrevious(jwtDTO), OK);
  }
}
