package com.bekiruzun.todoapp.controller;

import com.bekiruzun.todoapp.dto.UserDTO;
import com.bekiruzun.todoapp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "User Controller", tags = "User Controller")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ApiOperation("Authenticates user")
    public String login(@RequestBody UserDTO userDTO) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

    @PostMapping("/register")
    @ApiOperation("Register new user with given credentials")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return new ResponseEntity<>("{ \"message\": \"Registered successfully. You may login now.\"}", HttpStatus.OK);
    }
}
