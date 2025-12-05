package com.app.taqaseem.mapper;

import com.app.taqaseem.dto.RegisterUserResponseDTO;
import com.app.taqaseem.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "firstName", source = "name")
    RegisterUserResponseDTO toRegisterUserResponse(UserInfo userInfo);
}
