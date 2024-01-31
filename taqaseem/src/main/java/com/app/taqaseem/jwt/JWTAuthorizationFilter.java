package com.app.taqaseem.jwt;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.app.taqaseem.exception.InvalidJwtErrorException;
import com.app.taqaseem.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {
  private final JWTUtil jwtUtil;
  private final UserDetailsService userDetailsService;
  private final RedisUtil redisUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String jwt = authHeader.substring(7);
    final String phoneNumber = jwtUtil.extractPhoneNumber(jwt);
    if (phoneNumber == null) {
      log.error("Invalid access token, phone number is null");
      throw new InvalidJwtErrorException("Invalid access token, phone number is null");
    }

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(phoneNumber);

      if (!jwtUtil.isTokenValid(jwt, userDetails) || !redisUtil.isActiveAccessToken(jwt, phoneNumber)) {
        log.error("Invalid access token");
        throw new InvalidJwtErrorException("Invalid access token");
      }

      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(userDetails, null);
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    filterChain.doFilter(request, response);
  }
}
