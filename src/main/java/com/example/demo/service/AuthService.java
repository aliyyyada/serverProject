package com.example.demo.service;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Admin;
import com.example.demo.entity.AuthToken;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.Role;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.AuthTokenRepository;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final InstructorRepository instructorRepository;
    private final AuthTokenRepository authTokenRepository;

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

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        // проверка телефона
        userRepository.findByPhoneNumber(req.getPhoneNumber()).ifPresent(u -> {
            throw new IllegalArgumentException("Телефон уже зарегистрирован");
        });

        // создание user
        User user = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .patronymic(req.getPatronymic())
                .phoneNumber(req.getPhoneNumber())
                .password(req.getPassword())
                .role(req.getRole() == null ? Role.user : req.getRole())
                .isActive(true)
                .build();
        user = userRepository.save(user);

        // создание таблицы
        if (user.getRole() == Role.admin) {
            Admin admin = new Admin();
            admin.setUser(user);
            adminRepository.save(admin);
        } else if (user.getRole() == Role.instructor) {
            Instructor instructor = new Instructor();
            instructor.setUser(user);
            instructorRepository.save(instructor);
        }

        return issueToken(user);

    }

    @Transactional
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByPhoneNumber(req.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("Неверный телефон или пароль"));

        if (!user.getPassword().equals(req.getPassword())) {
            throw new IllegalArgumentException("Неверный телефон или пароль");
        }
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new IllegalArgumentException("Аккаунт неактивен");
        }

        return issueToken(user);
    }

    @Transactional
    public void logout(String token) {
        if (token == null || token.isBlank()) return;
        authTokenRepository.deleteByToken(token);
    }

    private AuthResponse issueToken(User user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setUser(user);
        authToken.setCreatedAt(LocalDateTime.now());
        authToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        authTokenRepository.save(authToken);

        return new AuthResponse(
                token,
                user.getId(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic()
        );
    }



}
