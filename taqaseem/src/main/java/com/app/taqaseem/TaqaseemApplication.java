package com.app.taqaseem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class TaqaseemApplication {
  public static void main(String[] args) {
    SpringApplication.run(TaqaseemApplication.class, args);
  }
}
