package com.app.taqaseem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeNameResponseDTO implements Serializable {
    private String messageEN;
    private String messageAR;
}
