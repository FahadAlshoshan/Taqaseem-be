package com.app.taqaseem.security.jwt;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.app.taqaseem.exception.InvalidJwtErrorException;
import com.app.taqaseem.security.TaqaseemUserDetails;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final HandlerExceptionResolver handlerExceptionResolver;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    try {
      final String jwt = authHeader.substring(7);
      final String userIdentifier = tokenService.extractUserIdentifier(jwt);
      if (userIdentifier == null) {
        log.error("Invalid access token, user identifier is null");
        throw new InvalidJwtErrorException("Invalid access token, user identifier is null");
      }

      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        TaqaseemUserDetails userDetails = this.tokenService.getUserDetailsFromToken(jwt);

        if (tokenService.isTokenInvalid(jwt, userDetails)
            || tokenService.isTokenInactive(jwt, userIdentifier)) {
          log.error("Invalid access token");
          throw new InvalidJwtErrorException("Invalid access token");
        }

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
      filterChain.doFilter(request, response);
    } catch (InvalidJwtErrorException | MalformedJwtException | UsernameNotFoundException e) {
      handlerExceptionResolver.resolveException(request, response, null, e);
    }
  }
}
