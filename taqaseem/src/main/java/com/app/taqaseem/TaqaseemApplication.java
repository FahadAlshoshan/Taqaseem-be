package com.app.taqaseem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class TaqaseemApplication {
  public static void main(String[] args) {
    SpringApplication.run(TaqaseemApplication.class, args);
  }
}
