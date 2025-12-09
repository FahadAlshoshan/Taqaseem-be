package com.app.taqaseem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserResponseDTO {
    private String firstName;
    private String username;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
