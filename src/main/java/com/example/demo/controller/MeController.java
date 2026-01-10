package com.example.demo.controller;

import com.example.demo.dto.MeResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MeController {
    @GetMapping("/me")
    public MeResponse me(Authentication authentication) {
        User u = (User) authentication.getPrincipal();

        return new MeResponse(
                u.getId(),
                u.getRole().toString(),
                u.getLastName(),
                u.getFirstName(),
                u.getPatronymic(),
                u.getPhoneNumber()
        );
    }
}
