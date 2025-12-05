package com.app.taqaseem.dto;

import com.app.taqaseem.validation.UniqueUsername;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.app.taqaseem.constant.Messages.USERNAME_PATTERN_VALIDATION_MESSAGE;
import static com.app.taqaseem.constant.Messages.USERNAME_SIZE_VALIDATION_MESSAGE;
import static com.app.taqaseem.constant.ValidationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserRequestDTO {
    @NotNull
    private String firstName;
    @Parameter(description = "Username to check (3-20 characters, letters, numbers, and underscores only)")
    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = USERNAME_SIZE_VALIDATION_MESSAGE)
    @Pattern(regexp = USERNAME_PATTERN, message = USERNAME_PATTERN_VALIDATION_MESSAGE)
    @UniqueUsername
    private String username;
}
