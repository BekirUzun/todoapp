package com.bekiruzun.todoapp.dao.repository;

import com.bekiruzun.todoapp.dao.entity.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.Optional;

public interface UserRepository extends CouchbaseRepository<User, Long> {
    //    User findByUsername(String username);
    Optional<User> findByUsername(String username);
}
