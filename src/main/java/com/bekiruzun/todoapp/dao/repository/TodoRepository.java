package com.bekiruzun.todoapp.dao.repository;

import com.bekiruzun.todoapp.dao.entity.TodoItem;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends CouchbaseRepository<TodoItem, Long> {

    @Override
    List<TodoItem> findAll();

    Optional<TodoItem> findById(String id);

    List<TodoItem> findByUserIdAndIsDeleted(String userId, boolean isDeleted);

    List<TodoItem> findByUserIdAndIsDeletedAndTitleContains(String userId, boolean isDeleted, String title);

    Optional<TodoItem> findById();

}
