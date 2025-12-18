package com.app.taqaseem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Profile("!local-clerk")
@RequiredArgsConstructor
public class TaqaseemAuthenticationProvider implements AuthenticationProvider {

    private final TaqaseemUserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getName();

        TaqaseemUserDetails user = userDetailService.loadUserByPhoneNumber(phoneNumber);

        if (!user.isEnabled()) {
            throw new BadCredentialsException("Account is disabled");
        }

        if (!user.isAccountNonLocked()) {
            throw new BadCredentialsException("Account is locked");
        }

        if (!user.isAccountNonExpired()) {
            throw new BadCredentialsException("Account has expired");
        }

        if (!user.isCredentialsNonExpired()) {
            throw new BadCredentialsException("Credentials have expired");
        }

        return new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}