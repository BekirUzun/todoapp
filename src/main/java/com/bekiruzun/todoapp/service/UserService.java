package com.bekiruzun.todoapp.service;

import com.bekiruzun.todoapp.common.MicroException;
import com.bekiruzun.todoapp.dao.entity.User;
import com.bekiruzun.todoapp.dao.repository.UserRepository;
import com.bekiruzun.todoapp.dto.RegisterDTO;
import com.bekiruzun.todoapp.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User loadUserByUsername(String username) throws MicroException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(!optionalUser.isPresent())
            throw new MicroException(2, "User with given username does not exists. Given username: " + username);
        return optionalUser.get();
    }

    public User getUser(String username) throws MicroException {
        User user = loadUserByUsername(username);
        user.setPassword(null);
        return user;
    }

    public void saveUser(RegisterDTO registerDTO) throws MicroException {
        Optional<User> optionalUser = userRepository.findByUsername(registerDTO.getUsername());
        if(optionalUser.isPresent())
            throw new MicroException(2, "User with given username already exists. Given username: " + registerDTO.getUsername());
        User user = userMapper.toEntity(registerDTO);
        user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
    }
}
