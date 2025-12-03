package com.app.taqaseem.service;

import com.app.taqaseem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsernameService {
    
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean isUsernameAvailable(String username) {
        log.debug("Checking availability for username: {}", username);
        boolean exists = userRepository.existsByUsername(username);
        log.debug("Username {} availability: {}", username, !exists);
        return !exists;
    }
}
