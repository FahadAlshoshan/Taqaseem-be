package com.app.taqaseem.controller;

import com.app.taqaseem.dto.CheckUsernameResponseDTO;
import com.app.taqaseem.service.UsernameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.app.taqaseem.constant.Messages.*;
import static com.app.taqaseem.constant.SwaggerApiExamples.*;
import static com.app.taqaseem.constant.ValidationConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Validated
@Tag(name = "Username", description = "Username availability and validation endpoints")
public class UsernameController {

    private final UsernameService usernameService;

    @GetMapping("/check-username")
    @Operation(
            summary = "Check username availability",
            description = "Checks if a username is available for registration. Username must be 3-20 characters and contain only letters, numbers, and underscores.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Username availability checked successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = CheckUsernameResponseDTO.class),
                                            examples = {
                                                    @ExampleObject(
                                                            name = "Available",
                                                            description = "Username is available",
                                                            value = API_EXAMPLE_200_CHECK_USERNAME_AVAILABLE),
                                                    @ExampleObject(
                                                            name = "Taken",
                                                            description = "Username is already taken",
                                                            value = API_EXAMPLE_200_CHECK_USERNAME_TAKEN)
                                            })
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid username format",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(
                                                    description = "Bad Request",
                                                    value = API_EXAMPLE_400_CHECK_USERNAME_INVALID))
                            })
            })
    public ResponseEntity<CheckUsernameResponseDTO> checkUsername(
            @Parameter(description = "Username to check (3-20 characters, letters, numbers, and underscores only)")
            @RequestParam
            @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = USERNAME_SIZE_VALIDATION_MESSAGE)
            @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_VALIDATION_MESSAGE)
            String username) {
        
        boolean isAvailable = usernameService.isUsernameAvailable(username);
        
        CheckUsernameResponseDTO response = CheckUsernameResponseDTO.builder()
                .username(username)
                .available(isAvailable)
                .message(isAvailable ? USERNAME_AVAILABLE_MESSAGE : USERNAME_TAKEN_MESSAGE)
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
