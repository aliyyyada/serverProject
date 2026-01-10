package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.Role;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final InstructorRepository instructorRepository;

    @Transactional
    public Map<String, Object> registerStaff(Map<String, String> body) {
        // 1) достаем поля
        String lastName = must(body, "lastName");
        String firstName = must(body, "firstName");
        String patronymic = must(body, "patronymic");

        String rawPhone = must(body, "phone");
        String phone = normalizePhone(rawPhone);

        String password = must(body, "password");
        String retryPassword = must(body, "retryPassword");

        String roleStr = must(body, "role").toLowerCase();
        Role role;

        if (roleStr.equals("admin")) {
            role = Role.admin;
        } else if (roleStr.equals("instructor")) {
            role = Role.instructor;
        } else {
            throw new IllegalArgumentException("Разрешены роли: admin или instructor");
        }
        // 2) базовая валидация
        if (phone == null || phone.length() != 11 || !phone.startsWith("7")) {
            throw new IllegalArgumentException("Телефон должен быть в формате 79991234567");
        }
        if (!password.equals(retryPassword)) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }
        if (!role.equals("admin") && !role.equals("instructor")) {
            throw new IllegalArgumentException("Разрешены роли: admin или instructor");
        }
        if (userRepository.existsByPhoneNumber(phone)) {
            throw new IllegalArgumentException("Пользователь с таким телефоном уже существует");
        }

        // 3) создаем User
        User user = User.builder()
                .firstName(firstName.trim())
                .lastName(lastName.trim())
                .patronymic(patronymic.trim())
                .phoneNumber(phone)
                .password(password)           // без шифровки как вы хотите
                .role(role)
                .isActive(true)
                .build();

        user = userRepository.save(user);

        // 4) создаем запись по роли
        if (role.equals("admin")) {
            Admin admin = new Admin();
            admin.setUser(user);
            adminRepository.save(admin);

            return Map.of(
                    "status", "ok",
                    "userId", user.getId(),
                    "role", "admin"
            );
        } else {
            Instructor instructor = new Instructor();
            instructor.setUser(user);
            // авто и КПП можно потом редактировать в профиле (по ТЗ)
            instructorRepository.save(instructor);

            return Map.of(
                    "status", "ok",
                    "userId", user.getId(),
                    "role", "instructor"
            );
        }
    }

    private static String must(Map<String, String> body, String key) {
        String v = body.get(key);
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalArgumentException("Поле '" + key + "' обязательно");
        }
        return v.trim();
    }

    private static String normalizePhone(String raw) {
        if (raw == null) return null;
        String digits = raw.replaceAll("\\D", "");
        if (digits.length() == 11 && digits.startsWith("8")) {
            digits = "7" + digits.substring(1);
        }
        return digits;
    }
}
