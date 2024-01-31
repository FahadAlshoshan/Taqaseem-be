package com.app.taqaseem.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTDTO implements Serializable {
  @JsonProperty("access_token")
  @NotNull
  private String accessToken;

  @JsonProperty("refresh_token")
  @NotNull
  private String refreshToken;
}
