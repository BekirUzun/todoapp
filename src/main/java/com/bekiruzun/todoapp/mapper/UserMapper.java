package com.bekiruzun.todoapp.mapper;

import com.bekiruzun.todoapp.dao.entity.User;
import com.bekiruzun.todoapp.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User entity = new User();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    public UserDTO toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
