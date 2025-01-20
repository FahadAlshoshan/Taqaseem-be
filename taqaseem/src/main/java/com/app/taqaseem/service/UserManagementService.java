package com.app.taqaseem.service;

import com.app.taqaseem.dto.ChangeNameRequestDTO;
import com.app.taqaseem.exception.UserNotFoundException;
import com.app.taqaseem.model.UserInfo;
import com.app.taqaseem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.app.taqaseem.constant.Messages.UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR;
import static com.app.taqaseem.constant.Messages.UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;

    public void changeName(ChangeNameRequestDTO changeNameRequestDTO, UserInfo user) {
        try {
            UserInfo userFromDB = userRepository.findByPhoneNumber(user.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException(UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN, UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR));
            userFromDB.setName(changeNameRequestDTO.getName());
            userRepository.save(userFromDB);
        } catch (Exception e) {
            throw new UserNotFoundException(UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN, UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR);
        }
    }
}
