package com.bekiruzun.todoapp.controller;

import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.dto.TodoItemCompleteDTO;
import com.bekiruzun.todoapp.dto.TodoItemDTO;
import com.bekiruzun.todoapp.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@Api(value = "Todo Controller", tags = "Todo Controller")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TodoController {

    TodoService todoService;

    @GetMapping
    @ApiOperation("Lists all not deleted todo items for logged in user")
    public ResponseEntity<List<TodoItemDTO>> getAllUserItems() {
        return new ResponseEntity<>(todoService.getAllUserItems(), HttpStatus.OK);
    }

    @GetMapping("/search")
    @ApiOperation("Searches todo items for logged in user")
    public ResponseEntity<List<TodoItemDTO>> search(@RequestParam String title) {
        return new ResponseEntity<>(todoService.search(title), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Gets todo item details by id")
    public ResponseEntity<TodoItemDTO> getById(@PathVariable String id) throws MicroException {
        return new ResponseEntity<>(todoService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Saves or updates given todo item")
    public ResponseEntity<TodoItemDTO> save(@RequestBody TodoItemDTO dto) throws MicroException {
        return new ResponseEntity<>(todoService.save(dto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Marks todo item as complete or incomplete")
    public ResponseEntity<TodoItemDTO> markTodoItemIsComplete(@PathVariable String id, @RequestBody TodoItemCompleteDTO dto)
            throws MicroException {
        return new ResponseEntity<>(todoService.markTodoItemIsComplete(id, dto.isComplete()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Marks todo item as deleted")
    public ResponseEntity<Void> delete(@PathVariable String id) throws MicroException {
        todoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
