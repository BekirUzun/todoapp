package com.bekiruzun.todoapp.service;

import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.common.TestUtils;
import com.bekiruzun.todoapp.dao.entity.TodoItem;
import com.bekiruzun.todoapp.dao.entity.User;
import com.bekiruzun.todoapp.dao.repository.TodoRepository;
import com.bekiruzun.todoapp.dto.TodoItemDTO;
import com.bekiruzun.todoapp.mapper.TodoItemMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration
class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;
    @Mock
    TodoItemMapper todoItemMapper;
    @InjectMocks
    TodoService todoService;

    @BeforeAll
    public static void setUp() {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(TestUtils.objectOf(User.class));
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    void getAllUserItems() {
        when(todoRepository.findByUserIdAndIsDeleted(any(), anyBoolean())).thenReturn(TestUtils.listOf(TodoItem.class));
        List<TodoItemDTO> expectedResult = TestUtils.listOf(TodoItemDTO.class);
        when(todoItemMapper.toDto(anyList())).thenReturn(expectedResult);

        List<TodoItemDTO> result = todoService.getAllUserItems();

        verify(todoRepository, times(1)).findByUserIdAndIsDeleted(any(), anyBoolean());
        assertEquals(expectedResult.get(0).getId(), result.get(0).getId());
    }

    @Test
    void search() {
        when(todoRepository.findByUserIdAndIsDeletedAndTitleContains(any(), anyBoolean(), any())).thenReturn(TestUtils.listOf(TodoItem.class));
        List<TodoItemDTO> expectedResult = TestUtils.listOf(TodoItemDTO.class);
        when(todoItemMapper.toDto(anyList())).thenReturn(expectedResult);

        List<TodoItemDTO> result = todoService.search("1");

        verify(todoRepository, times(1)).findByUserIdAndIsDeletedAndTitleContains(any(), anyBoolean(), any());
        assertEquals(expectedResult.get(0).getId(), result.get(0).getId());
    }

    @Test
    void getById() {
        when(todoRepository.findById(anyString())).thenReturn(TestUtils.optionalOf(TodoItem.class));
        TodoItemDTO expectedResult = TestUtils.objectOf(TodoItemDTO.class);
        when(todoItemMapper.toDto(any(TodoItem.class))).thenReturn(expectedResult);

        TodoItemDTO result = todoService.getById("1");

        verify(todoItemMapper, times(1)).toDto(any(TodoItem.class));
        assertEquals(expectedResult.getId(), result.getId());
    }

    @Test
    void getById_notFound() {
        MicroException exception = assertThrows(MicroException.class, () -> todoService.getById("-1"));
    }

    @Test
    void save() {
        TodoItem todoItem = TestUtils.objectOf(TodoItem.class);
        todoItem.setCompleted(false);
        when(todoItemMapper.toEntity(any(TodoItemDTO.class))).thenReturn(todoItem);
        when(todoRepository.save(any())).thenReturn(todoItem);
        TodoItemDTO expectedResult = TestUtils.objectOf(TodoItemDTO.class);
        when(todoItemMapper.toDto(any(TodoItem.class))).thenReturn(expectedResult);

        TodoItemDTO saveDto = TestUtils.objectOf(TodoItemDTO.class);
        saveDto.setId(null);
        saveDto.setCompleted(false);
        TodoItemDTO result = todoService.save(saveDto);

        verify(todoRepository, times(1)).save(any());
        assertEquals(expectedResult.getId(), result.getId());
    }

    @Test
    void save_completed() {
        TodoItem todoItem = TestUtils.objectOf(TodoItem.class);
        todoItem.setCompleted(true);
        todoItem.setCompleteDate(null);
        when(todoItemMapper.toEntity(any(TodoItemDTO.class))).thenReturn(todoItem);
        when(todoRepository.save(any())).thenReturn(todoItem);
        TodoItemDTO expectedResult = TestUtils.objectOf(TodoItemDTO.class);
        when(todoItemMapper.toDto(any(TodoItem.class))).thenReturn(expectedResult);

        TodoItemDTO saveDto = TestUtils.objectOf(TodoItemDTO.class);
        saveDto.setId(null);
        TodoItemDTO result = todoService.save(saveDto);

        verify(todoRepository, times(1)).save(any());
        assertEquals(expectedResult.getId(), result.getId());
    }

    @Test
    void save_withId() {
        TodoItem todoItem = TestUtils.objectOf(TodoItem.class);
        when(todoItemMapper.toEntity(any(TodoItemDTO.class))).thenReturn(todoItem);
        when(todoRepository.findById(anyString())).thenReturn(Optional.of(todoItem));
        when(todoRepository.save(any())).thenReturn(todoItem);
        TodoItemDTO expectedResult = TestUtils.objectOf(TodoItemDTO.class);
        when(todoItemMapper.toDto(any(TodoItem.class))).thenReturn(expectedResult);

        TodoItemDTO saveDto = TestUtils.objectOf(TodoItemDTO.class);
        TodoItemDTO result = todoService.save(saveDto);

        verify(todoItemMapper, times(1)).toDto(any(TodoItem.class));
        verify(todoItemMapper, times(1)).toEntity(any(TodoItemDTO.class));
        assertEquals(expectedResult.getId(), result.getId());
    }

    @Test
    void markTodoItemIsComplete() {
        TodoItem todoItem = TestUtils.objectOf(TodoItem.class);
        todoItem.setCompleted(false);
        when(todoRepository.findById(anyString())).thenReturn(Optional.of(todoItem));
        when(todoRepository.save(any())).thenReturn(todoItem);
        TodoItemDTO expectedResult = TestUtils.objectOf(TodoItemDTO.class);
        when(todoItemMapper.toDto(any(TodoItem.class))).thenReturn(expectedResult);

        TodoItemDTO result = todoService.markTodoItemIsComplete("1", true);

        verify(todoRepository, times(1)).save(any());
        assertEquals(expectedResult.getId(), result.getId());
    }

    @Test
    void delete() {
        TodoItem todoItem = TestUtils.objectOf(TodoItem.class);
        when(todoRepository.findById(anyString())).thenReturn(Optional.of(todoItem));
        when(todoRepository.save(any())).thenReturn(todoItem);

        todoService.delete("1");

        verify(todoRepository, times(1)).save(any());
    }


}