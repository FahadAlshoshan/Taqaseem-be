package com.app.taqaseem.security.jwt;

import static com.app.taqaseem.constant.SwaggerApiExamples.API_EXAMPLE_200_REFRESH_JWT;
import static com.app.taqaseem.constant.SwaggerApiExamples.API_EXAMPLE_401_REFRESH_JWT;
import static org.springframework.http.HttpStatus.OK;

import com.app.taqaseem.security.jwt.impl.CustomAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/jwt")
@Profile("!local-clerk")
public class JWTController {
  private final CustomAuthenticationService authService;

  @PostMapping("/refresh")
  @Operation(
      summary = "Generate access token via refresh token",
      description =
          "Generate new access token and invalidate any access token previously associated with the user by sending the refresh token")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "New access token generated",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = JWTDTO.class),
                  examples = {
                    @ExampleObject(description = "OK", value = API_EXAMPLE_200_REFRESH_JWT),
                  })
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Error occurred while attempting to generate JWT",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = JWTDTO.class),
                  examples = {
                    @ExampleObject(
                        description = "UNAUTHORIZED",
                        value = API_EXAMPLE_401_REFRESH_JWT)
                  })
            })
      })
  public ResponseEntity<?> refreshToken(@Valid @RequestBody JWTDTO jwtDTO) {
    return new ResponseEntity<>(authService.refreshAccessToken(jwtDTO), OK);
  }
}
