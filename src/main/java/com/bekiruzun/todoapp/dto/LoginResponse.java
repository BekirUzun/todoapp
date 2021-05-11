package com.bekiruzun.todoapp.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String username;
    private String jwtToken;
}
