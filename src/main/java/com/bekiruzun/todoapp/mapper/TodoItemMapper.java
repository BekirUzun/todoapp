package com.bekiruzun.todoapp.mapper;

import com.bekiruzun.todoapp.dao.entity.TodoItem;
import com.bekiruzun.todoapp.dto.TodoItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoItemMapper {

    TodoItemDTO toDto(TodoItem entity);

    List<TodoItemDTO> toDto(List<TodoItem> entityList);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "completeDate", ignore = true)
    TodoItem toEntity(TodoItemDTO dto);

    List<TodoItem> toEntity(List<TodoItemDTO> dtoList);
}
