package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user){
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()){
            throw new RuntimeException("Этот номер телефона уде используется.");
        }
        user.setActive(true);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByPhone(String phone){
        return userRepository.findByPhoneNumber(phone);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public void deactivateUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден."));
        user.setActive(false);
        userRepository.save(user);
    }





}
