package com.bekiruzun.todoapp.mapper;


import com.bekiruzun.todoapp.dao.entity.TodoItem;
import com.bekiruzun.todoapp.dto.TodoItemDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TodoItemMapper {

    public TodoItemDTO toDto(TodoItem entity) {
        if ( entity == null ) {
            return null;
        }

        TodoItemDTO todoItemDTO = new TodoItemDTO();

//        todoItemDTO.setId( entity.getId() );
//        todoItemDTO.setContent( entity.getContent() );
//        todoItemDTO.setCompleted( entity.isCompleted() );
//        todoItemDTO.setCreateDate( entity.getCreateDate() );
//        todoItemDTO.setCompleteDate( entity.getCompleteDate() );

        return todoItemDTO;
    }

//    List<TodoItemDTO> toDto(List<TodoItem> entityList);
//
//    TodoItem toEntity(TodoItemDTO dto);
//    List<TodoItem> toEntity(List<TodoItemDTO> dtoList);

}
