package com.bekiruzun.todoapp.dao.repository;

import com.bekiruzun.todoapp.dao.entity.TodoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends CouchbaseRepository<TodoItem, Long> {

    @Override
    List<TodoItem> findAll();

    Optional<TodoItem> findById(String id);

    List<TodoItem> findByUserId(String userId);

    Optional<TodoItem> findById();

}
