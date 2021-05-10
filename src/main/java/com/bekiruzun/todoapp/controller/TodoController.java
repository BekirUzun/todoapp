package com.bekiruzun.todoapp.controller;

import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.dao.entity.TodoItem;
import com.bekiruzun.todoapp.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@Api(value = "Todo Controller", tags = "Todo Controller")
public class TodoController {
    @Autowired
    TodoService todoService;

    @GetMapping("/home")
    @ApiOperation("Returns Hello World")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("Hello Docker World 5311", HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Lists all todo items")
    public ResponseEntity<List<TodoItem>> getAll() {
        return new ResponseEntity<>(todoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getById(@PathVariable Long id) throws MicroException {
        return new ResponseEntity<>(todoService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TodoItem> save(@RequestBody TodoItem dto) throws MicroException {
        return new ResponseEntity<>(todoService.save(dto), HttpStatus.OK);
    }


}
