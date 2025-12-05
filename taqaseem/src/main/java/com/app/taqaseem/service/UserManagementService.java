package com.app.taqaseem.service;

import com.app.taqaseem.dto.ChangeNameRequestDTO;
import com.app.taqaseem.dto.RegisterUserRequestDTO;
import com.app.taqaseem.dto.RegisterUserResponseDTO;
import com.app.taqaseem.exception.UserAlreadyExistsException;
import com.app.taqaseem.exception.UserNotFoundException;
import com.app.taqaseem.mapper.UserMapper;
import com.app.taqaseem.model.UserInfo;
import com.app.taqaseem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.app.taqaseem.constant.Messages.*;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void changeName(ChangeNameRequestDTO changeNameRequestDTO, UserInfo user) {
        try {
            UserInfo userFromDB = userRepository.findByPhoneNumber(user.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException(UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN, UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR));
            userFromDB.setName(changeNameRequestDTO.getName());
            userRepository.save(userFromDB);
        } catch (Exception e) {
            throw new UserNotFoundException(UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN, UN_SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR);
        }
    }

    public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO, UserInfo user) {
        UserInfo userFromDB = userRepository.findByPhoneNumber(user.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException(UNSUCCESSFUL_USER_REGISTRATION_MESSAGE_EN, UNSUCCESSFUL_USER_REGISTRATION_MESSAGE_AR));

        if (userFromDB.isRegistered())
            throw new UserAlreadyExistsException(USER_ALREADY_REGISTERED_EN); //TODO: implement localization for messages.

        userFromDB.setUsername(registerUserRequestDTO.getUsername());
        userFromDB.setName(registerUserRequestDTO.getFirstName());
        userFromDB.setIsRegistered(true);

        UserInfo savedUser = userRepository.save(userFromDB);

        return userMapper.toRegisterUserResponse(savedUser);
    }
}
