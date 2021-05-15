package com.bekiruzun.todoapp.controller;

import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.common.TestUtils;
import com.bekiruzun.todoapp.config.MicroExceptionHandler;
import com.bekiruzun.todoapp.dto.RegisterDTO;
import com.bekiruzun.todoapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    private final String URI = "/user";
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private UserService userService;

    @BeforeAll
    public void setUp() {
        userService = mock(UserService.class);
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new MicroExceptionHandler())
                .build();
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(post(URI + "/login")
                .content(objectMapper.writeValueAsString(TestUtils.objectOf(RegisterDTO.class)))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MicroException));

    }

    @Test
    void register() throws Exception {
        MvcResult result = mockMvc.perform(post(URI + "/register")
                .content(objectMapper.writeValueAsString(TestUtils.objectOf(RegisterDTO.class)))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultStr = result.getResponse().getContentAsString();

        verify(userService, times(1)).saveUser(any());
        assertNotNull(resultStr);
        assertTrue(resultStr.contains("successful"));
    }
}