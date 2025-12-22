package com.example.demo.service;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;

    public User login(String phone, String password){
        User user = userRepository.findByPhoneNumber(phone)
                .orElseThrow(() ->  new RuntimeException("Пользователь не найден."));
        if (!user.getPassword().equals(password)){
            throw new RuntimeException("Неверный пароль.");

        }

        if (user.getIsActive()==false){
            throw new RuntimeException("Пользователь заблокирован.");
        }
        return user;
    }



}
