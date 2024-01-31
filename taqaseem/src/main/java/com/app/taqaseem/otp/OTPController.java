package com.app.taqaseem.otp;

import static com.app.taqaseem.constant.SwaggerApiExamples.API_EXAMPLE_200_OTP_AUTHENTICATE;
import static com.app.taqaseem.constant.SwaggerApiExamples.API_EXAMPLE_401_OTP_AUTHENTICATE;
import static com.app.taqaseem.constant.SwaggerApiExamples.API_EXAMPLE_500_OTP_GENERATE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/otp")
public class OTPController {

  private final OTPService otpService;

  @PostMapping("/generate")
  @Operation(
      summary = "Generate OTP for phone number",
      description = "Generate OTP for phone number validation")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OTP generated"),
        @ApiResponse(
            responseCode = "500",
            description = "Error occurred while attempting to generate OTP",
            content = {
              @Content(
                  mediaType = "application/json",
                  examples =
                      @ExampleObject(
                          description = "INTERNAL SERVER ERROR",
                          value = API_EXAMPLE_500_OTP_GENERATE))
            })
      })
  public void generateOTP(@Valid @RequestBody OTPGenerateRequestDTO otpGenerateRequestDTO) {
    otpService.generateOTP(otpGenerateRequestDTO);
  }

  @PostMapping("/authenticate")
  @Operation(
      summary = "Authenticate OTP via phone number",
      description = "Validate sent OTP with previously generated OTP for associated phoen number")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OTP validated",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = OTPAuthenticateResponseDTO.class),
                  examples =
                      @ExampleObject(description = "OK", value = API_EXAMPLE_200_OTP_AUTHENTICATE))
            }),
        @ApiResponse(
            responseCode = "401",
            description = "OTP not validated",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = OTPAuthenticateResponseDTO.class),
                  examples =
                      @ExampleObject(description = "OK", value = API_EXAMPLE_401_OTP_AUTHENTICATE))
            })
      })
  public ResponseEntity<?> validateOTP(
      @Valid @RequestBody OTPAuthenticateRequestDTO otpAuthenticateRequestDTO) {
    OTPAuthenticateResponseDTO otpAuthenticateResponseDTO =
        otpService.authenticateOTP(otpAuthenticateRequestDTO);

    return new ResponseEntity<>(
        otpAuthenticateResponseDTO,
        otpAuthenticateResponseDTO.isAuthenticated() ? OK : UNAUTHORIZED);
  }
}
