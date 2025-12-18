package com.app.taqaseem.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface TaqaseemUserDetails extends Serializable {
    String getId();
    String getPhoneNumber();
    String getUsername();
    String getName();
    Collection<? extends GrantedAuthority> getAuthorities();
    boolean isEnabled();
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isRegistered();
}
