package com.bekiruzun.todoapp.service;

import com.bekiruzun.todoapp.dao.entity.User;
import com.bekiruzun.todoapp.dao.repository.UserRepository;
import com.bekiruzun.todoapp.dto.RegisterDTO;
import com.bekiruzun.todoapp.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User getUser(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        user.setPassword(null);
        return user;
    }

    public void saveUser(RegisterDTO registerDTO) {
        User user = userMapper.toEntity(registerDTO);
        user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
    }
}
