package com.bekiruzun.todoapp.service;


import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.dao.entity.TodoItem;
import com.bekiruzun.todoapp.dao.repository.TodoRepository;
import com.bekiruzun.todoapp.dto.TodoItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Slf4j
public class TodoService {
    @Autowired
    TodoRepository repository;
//    @Autowired
//    TodoItemMapper mapper;

    public List<TodoItem> getAll() {
        System.out.println("sys Getting all TODO items");
        return repository.findAll();
    }

    public TodoItem getById(Long id) throws MicroException {
        Optional<TodoItem> optionalEntity = repository.findById(id);
        if(!optionalEntity.isPresent())
            throw new MicroException(1L, "Entity with given ID not found");

        return optionalEntity.get();
    }

    public TodoItem save(TodoItem dto) {
        return repository.save(dto);
    }

}
