package com.app.taqaseem.security;

public interface TaqaseemUserDetailService {
    TaqaseemUserDetails loadUserByPhoneNumber(String phoneNumber);

    TaqaseemUserDetails loadUserById(Long id);

    TaqaseemUserDetails loadUserByUsername(String username);

    TaqaseemUserDetails loadUserByName(String name);

    boolean existsByPhoneNumber(String phoneNumber);
}