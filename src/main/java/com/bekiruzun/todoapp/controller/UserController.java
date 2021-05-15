package com.bekiruzun.todoapp.controller;

import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.dto.RegisterDTO;
import com.bekiruzun.todoapp.dto.RegisterResponse;
import com.bekiruzun.todoapp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "User Controller", tags = "User Controller")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    UserService userService;

    @PostMapping("/login")
    @ApiOperation("Authenticates user")
    public ResponseEntity<String> login(@RequestBody RegisterDTO registerDTO) throws MicroException {
        throw new MicroException(3, "This method shouldn't be called. It's implemented by Spring Security filters.");
    }

    @PostMapping("/register")
    @ApiOperation("Register new user with given credentials")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterDTO registerDTO) throws MicroException {
        userService.saveUser(registerDTO);
        RegisterResponse response = new RegisterResponse("Registered successfully. You may login now.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
