package com.bekiruzun.todoapp.controller;

import com.bekiruzun.todoapp.common.TestUtils;
import com.bekiruzun.todoapp.config.MicroExceptionHandler;
import com.bekiruzun.todoapp.dto.TodoItemCompleteDTO;
import com.bekiruzun.todoapp.dto.TodoItemDTO;
import com.bekiruzun.todoapp.service.TodoService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodoControllerTest {

    private final String URI = "/todo";
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private TodoService todoService;

    @BeforeAll
    public void setUp() {
        todoService = mock(TodoService.class);
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        mockMvc = MockMvcBuilders.standaloneSetup(new TodoController(todoService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new MicroExceptionHandler())
                .build();
    }

    @Test
    void getAllUserItems() throws Exception {
        List<TodoItemDTO> expectedResult = TestUtils.listOf(TodoItemDTO.class);
        when(todoService.getAllUserItems()).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultStr = result.getResponse().getContentAsString();

        verify(todoService, times(1)).getAllUserItems();
        assertTrue(objectMapper.writeValueAsString(expectedResult).trim().equalsIgnoreCase(resultStr.trim()));
    }

    @Test
    void search() throws Exception {
        List<TodoItemDTO> expectedResult = TestUtils.listOf(TodoItemDTO.class);
        when(todoService.search(any())).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(get(URI + "/search")
                .param("title", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultStr = result.getResponse().getContentAsString();

        verify(todoService, times(1)).search(any());
        assertTrue(objectMapper.writeValueAsString(expectedResult).equalsIgnoreCase(resultStr));
    }

    @Test
    void getById() throws Exception {
        TodoItemDTO expectedResult = TestUtils.objectOf(TodoItemDTO.class);
        when(todoService.getById(any())).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(get(URI + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultStr = result.getResponse().getContentAsString();

        verify(todoService, times(1)).getById(any());
        assertTrue(objectMapper.writeValueAsString(expectedResult).equalsIgnoreCase(resultStr));
    }

    @Test
    void save() throws Exception {
        TodoItemDTO expectedResult = TestUtils.objectOf(TodoItemDTO.class);
        when(todoService.save(any())).thenReturn(expectedResult);

        MvcResult result = mockMvc.perform(post(URI)
                .content(objectMapper.writeValueAsString(expectedResult))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultStr = result.getResponse().getContentAsString();

        verify(todoService, times(1)).save(any());
        assertTrue(objectMapper.writeValueAsString(expectedResult).equalsIgnoreCase(resultStr));
    }

    @Test
    void markTodoItemIsComplete() throws Exception {
        TodoItemDTO expectedResult = TestUtils.objectOf(TodoItemDTO.class);
        when(todoService.markTodoItemIsComplete(any(), anyBoolean())).thenReturn(expectedResult);

        String requestBody = objectMapper.writeValueAsString(TestUtils.objectOf(TodoItemCompleteDTO.class));
        MvcResult result = mockMvc.perform(patch(URI + "/1")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultStr = result.getResponse().getContentAsString();

        verify(todoService, times(1)).markTodoItemIsComplete(any(), anyBoolean());
        assertTrue(objectMapper.writeValueAsString(expectedResult).equalsIgnoreCase(resultStr));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent()).andReturn();

        verify(todoService, times(1)).delete(any());
    }


}