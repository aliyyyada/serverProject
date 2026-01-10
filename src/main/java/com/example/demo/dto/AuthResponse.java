package com.example.demo.dto;
import com.example.demo.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private Role role;
    private String firstName;
    private String lastName;
    private String patronymic;
}
