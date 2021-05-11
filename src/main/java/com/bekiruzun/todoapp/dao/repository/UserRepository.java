package com.bekiruzun.todoapp.dao.repository;

import com.bekiruzun.todoapp.dao.entity.TodoItem;
import com.bekiruzun.todoapp.dao.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
