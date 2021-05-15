package com.bekiruzun.todoapp.service;

import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.common.TestUtils;
import com.bekiruzun.todoapp.dao.entity.User;
import com.bekiruzun.todoapp.dao.repository.UserRepository;
import com.bekiruzun.todoapp.dto.RegisterDTO;
import com.bekiruzun.todoapp.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @InjectMocks
    UserService userService;

    @Test
    void loadUserByUsername() {
        User expectedResult = TestUtils.objectOf(User.class);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(expectedResult));

        User result = userService.loadUserByUsername("username");

        verify(userRepository, times(1)).findByUsername(any());
        assertEquals(expectedResult.getId(), result.getId());
        assertNotNull(result.getPassword());
    }

    @Test
    void loadUserByUsername_userNotExistException() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        assertThrows(MicroException.class, () -> userService.loadUserByUsername("idontexist"));

        verify(userRepository, times(1)).findByUsername(any());
    }

    @Test
    void getUser() {
        User expectedResult = TestUtils.objectOf(User.class);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(expectedResult));

        User result = userService.getUser("username");

        verify(userRepository, times(1)).findByUsername(any());
        assertEquals(expectedResult.getId(), result.getId());
        assertNull(result.getPassword());
    }

    @Test
    void saveUser() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userMapper.toEntity(any())).thenReturn(TestUtils.objectOf(User.class));
        when(bCryptPasswordEncoder.encode(any())).thenReturn("ngn0s98dgfbsvd");

        userService.saveUser(TestUtils.objectOf(RegisterDTO.class));

        verify(userRepository, times(1)).findByUsername(any());
        verify(bCryptPasswordEncoder, times(1)).encode(any());
        verify(userRepository, times(1)).save(any());

    }

    @Test
    void saveUser_userAlreadyExists() {
        when(userRepository.findByUsername(any())).thenReturn(TestUtils.optionalOf(User.class));

        assertThrows(MicroException.class, () -> userService.saveUser(TestUtils.objectOf(RegisterDTO.class)));

        verify(userRepository, times(1)).findByUsername(any());
        verify(userRepository, times(0)).save(any());
    }
}