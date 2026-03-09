package com.fenix.platform.mapper;

import com.fenix.platform.dto.UserRequestDTO;
import com.fenix.platform.dto.UserResponseDTO;
import com.fenix.platform.entity.User;

public class UserMapper {

public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }

public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}

