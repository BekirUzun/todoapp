package com.bekiruzun.todoapp.dao.repository;

import com.bekiruzun.todoapp.dao.entity.TodoItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends CrudRepository<TodoItem, Long> {

    @Override
    List<TodoItem> findAll();

    Optional<TodoItem> findById();

}
