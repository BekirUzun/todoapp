package com.bekiruzun.todoapp.service;


import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.dao.entity.TodoItem;
import com.bekiruzun.todoapp.dao.repository.TodoRepository;
import com.bekiruzun.todoapp.dto.TodoItemDTO;
import com.bekiruzun.todoapp.mapper.TodoItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Slf4j
public class TodoService {
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TodoItemMapper todoItemMapper;

    public List<TodoItemDTO> getAll() {
        System.out.println("sys Getting all TODO items");
        return todoItemMapper.toDto(todoRepository.findAll());
    }

    public TodoItemDTO getById(Long id) throws MicroException {
        Optional<TodoItem> optionalEntity = todoRepository.findById(id);
        if(!optionalEntity.isPresent())
            throw new MicroException(1L, "Entity with given ID not found");

        return todoItemMapper.toDto(optionalEntity.get());
    }

    public TodoItemDTO save(TodoItemDTO dto) {
        return todoItemMapper.toDto(todoRepository.save(todoItemMapper.toEntity(dto)));
    }

}
