package com.app.taqaseem.controller;

import com.app.taqaseem.dto.ChangeNameRequestDTO;
import com.app.taqaseem.dto.ChangeNameResponseDTO;
import com.app.taqaseem.model.UserInfo;
import com.app.taqaseem.service.UserManagementService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.app.taqaseem.constant.Messages.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api/v1/taqaseem/user")
public class UserManagementController {
    private final UserManagementService userManagementService;

    @PostMapping("/changeName")
    ResponseEntity<?> changeName(@Valid @RequestBody ChangeNameRequestDTO changeNameRequestDTO, @AuthenticationPrincipal UserInfo user) {
        userManagementService.changeName(changeNameRequestDTO, user);
        return new ResponseEntity<>(ChangeNameResponseDTO.builder().messageEN(SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_EN).messageAR(SUCCESSFUL_USER_NAME_CHANGE_MESSAGE_AR).build(), HttpStatus.OK);
    }
}
