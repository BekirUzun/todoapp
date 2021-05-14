package com.bekiruzun.todoapp.mapper;

import com.bekiruzun.todoapp.dao.entity.User;
import com.bekiruzun.todoapp.dto.RegisterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterDTO dto);

}
