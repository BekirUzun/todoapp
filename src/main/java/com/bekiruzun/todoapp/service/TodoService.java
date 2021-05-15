package com.bekiruzun.todoapp.service;


import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.common.SecurityUtil;
import com.bekiruzun.todoapp.dao.entity.TodoItem;
import com.bekiruzun.todoapp.dao.repository.TodoRepository;
import com.bekiruzun.todoapp.dto.TodoItemDTO;
import com.bekiruzun.todoapp.mapper.TodoItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoItemMapper todoItemMapper;

    public List<TodoItemDTO> getAllUserItems() {
        return todoItemMapper.toDto(todoRepository.findByUserIdAndIsDeleted(SecurityUtil.getUserId(), false));
    }

    public List<TodoItemDTO> search(String title) {
        return todoItemMapper.toDto(todoRepository.findByUserIdAndIsDeletedAndTitleContains(SecurityUtil.getUserId(), false, title));
    }

    public TodoItemDTO getById(String id) {
        return todoItemMapper.toDto(findById(id));
    }

    public TodoItemDTO save(TodoItemDTO dto) {
        TodoItem todoItem = todoItemMapper.toEntity(dto);
        if(dto.getId() != null) {
            findById(dto.getId());
        } else {
            todoItem.setCreateDate(new Date());
        }

        return todoItemMapper.toDto(save(todoItem));
    }

    public TodoItemDTO markTodoItemIsComplete(String id, boolean isComplete) {
        TodoItem todoItem = findById(id);
        todoItem.setCompleted(isComplete);
        return todoItemMapper.toDto(save(todoItem));
    }

    public void delete(String id) {
        TodoItem todoItem = findById(id);
        todoItem.setDeleted(true);  // soft delete
        todoRepository.save(todoItem);
    }

    private TodoItem save(TodoItem todoItem) {
        if(todoItem.isCompleted() && todoItem.getCompleteDate() == null)
            todoItem.setCompleteDate(new Date());
        else if (!todoItem.isCompleted())
            todoItem.setCompleteDate(null);

        todoItem.setUserId(SecurityUtil.getUserId());
        return todoRepository.save(todoItem);
    }

    private TodoItem findById(String id) {
        Optional<TodoItem> optionalEntity = todoRepository.findById(id);
        if(!optionalEntity.isPresent())
            throw new MicroException(1, "Entity with given ID not found");
        return optionalEntity.get();
    }

}
