package com.app.taqaseem.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@Builder
public class ApiResponse implements Serializable {
    private String messageEN;
    private String messageAR;
}
