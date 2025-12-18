package com.app.taqaseem.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.app.taqaseem.security.jwt.AuthorizationFilter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private static final String[] WHITE_LIST_URL = {
    "/api/v1/auth/**",
    "/v2/api-docs",
    "/v3/api-docs",
    "/v3/api-docs/**",
    "/swagger-resources",
    "/swagger-resources/**",
    "/configuration/ui",
    "/configuration/security",
    "/swagger-ui/**",
    "/webjars/**",
    "/swagger-ui.html",
  };
  private final AuthorizationFilter authorizationFilter;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  private final Optional<TaqaseemAuthenticationProvider> authenticationProvider; // Made optional

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    var httpSecurity =
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                req ->
                    req.requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers("/api/v1/taqaseem/**")
                        .authenticated())
            .exceptionHandling(
                exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

    authenticationProvider.ifPresent(httpSecurity::authenticationProvider);

    return httpSecurity
        .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
