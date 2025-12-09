package com.app.taqaseem.controller;

import com.app.taqaseem.dto.ChangeNameRequestDTO;
import com.app.taqaseem.dto.ChangeNameResponseDTO;
import com.app.taqaseem.dto.RegisterUserRequestDTO;
import com.app.taqaseem.dto.RegisterUserResponseDTO;
import com.app.taqaseem.model.UserInfo;
import com.app.taqaseem.service.UserManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.app.taqaseem.constant.Messages.SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR;
import static com.app.taqaseem.constant.Messages.SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN;
import static com.app.taqaseem.constant.SwaggerApiExamples.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api/v1/taqaseem/user")
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PostMapping("/changeName")
    ResponseEntity<?> changeName(@Valid @RequestBody ChangeNameRequestDTO changeNameRequestDTO, @AuthenticationPrincipal UserInfo user) {
        userManagementService.changeName(changeNameRequestDTO, user);
        return new ResponseEntity<>(ChangeNameResponseDTO.builder().messageEN(SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN).messageAR(SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR).build(), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register user")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered successfully",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = RegisterUserResponseDTO.class),
                                            examples = {
                                                    @ExampleObject(
                                                            name = "Available",
                                                            value = API_EXAMPLE_200_REGISTER_USER),
                                            })
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(
                                                    description = "Username already taken",
                                                    value = API_EXAMPLE_400_REGISTER_USER_USERNAME_TAKEN)),
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(
                                                    description = "User already registered",
                                                    value = API_EXAMPLE_400_REGISTER_USER_ALREADY_REGISTERED))
                            }),

            })
    ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequestDTO registerUserRequestDTO, @AuthenticationPrincipal UserInfo user) {
        return new ResponseEntity<>(userManagementService.registerUser(registerUserRequestDTO, user), HttpStatus.OK);
    }
}
