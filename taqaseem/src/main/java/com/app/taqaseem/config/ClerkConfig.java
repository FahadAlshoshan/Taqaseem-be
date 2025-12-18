package com.app.taqaseem.config;

import com.clerk.backend_api.Clerk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local-clerk")
public class ClerkConfig {

    @Value("${clerk.secret-key}")
    private String secretKey;

    @Bean
    public Clerk clerkClient() {
        return Clerk.builder()
                .bearerAuth(secretKey)
                .build();
    }
}