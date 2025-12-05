package com.app.taqaseem.security;

import com.app.taqaseem.exception.UserNotFoundException;
import com.app.taqaseem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.app.taqaseem.constant.Messages.USER_NOT_FOUND_MESSAGE_AR;
import static com.app.taqaseem.constant.Messages.USER_NOT_FOUND_MESSAGE_EN;

@RequiredArgsConstructor
@Service
@Slf4j
public class TaqaseemUserDetailsServiceImpl implements TaqaseemUserDetailService {
  private final UserRepository userRepository;


    @Override
    public TaqaseemUserDetails loadUserByPhoneNumber(String phoneNumber) {
        return userRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE_EN, USER_NOT_FOUND_MESSAGE_AR));
    }

    @Override
    public TaqaseemUserDetails loadUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE_EN, USER_NOT_FOUND_MESSAGE_AR));    }

    @Override
    public TaqaseemUserDetails loadUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE_EN, USER_NOT_FOUND_MESSAGE_AR));
    }

    @Override
    public TaqaseemUserDetails loadUserByName(String name) {
        return userRepository
                .findByName(name)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE_EN, USER_NOT_FOUND_MESSAGE_AR));    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
}
