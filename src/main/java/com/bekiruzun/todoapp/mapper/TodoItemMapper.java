package com.bekiruzun.todoapp.mapper;


import com.bekiruzun.todoapp.dao.entity.TodoItem;
import com.bekiruzun.todoapp.dto.TodoItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TodoItemMapper {

    public TodoItemDTO toDto(TodoItem entity) {
        if ( entity == null ) {
            return null;
        }

        TodoItemDTO todoItemDTO = new TodoItemDTO();

        todoItemDTO.setId( entity.getId() );
        todoItemDTO.setContent( entity.getContent() );
        todoItemDTO.setCompleted( entity.isCompleted() );
        todoItemDTO.setCreateDate( entity.getCreateDate() );
        todoItemDTO.setCompleteDate( entity.getCompleteDate() );

        return todoItemDTO;
    }


    public List<TodoItemDTO> toDto(List<TodoItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TodoItemDTO> list = new ArrayList<TodoItemDTO>( entityList.size() );
        for ( TodoItem todoItem : entityList ) {
            list.add( toDto( todoItem ) );
        }

        return list;
    }

    public TodoItem toEntity(TodoItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TodoItem todoItem = new TodoItem();

        todoItem.setId( dto.getId() );
        todoItem.setContent( dto.getContent() );
        todoItem.setCompleted( dto.isCompleted() );
        todoItem.setCreateDate( dto.getCreateDate() );
        todoItem.setCompleteDate( dto.getCompleteDate() );

        return todoItem;
    }

    public List<TodoItem> toEntity(List<TodoItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TodoItem> list = new ArrayList<TodoItem>( dtoList.size() );
        for ( TodoItemDTO todoItemDTO : dtoList ) {
            list.add( toEntity( todoItemDTO ) );
        }

        return list;
    }

}
