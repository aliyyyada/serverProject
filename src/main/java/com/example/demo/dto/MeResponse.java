package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data @AllArgsConstructor
public class MeResponse {
    private Long userId;
    private String role;

    private String lastName;
    private String firstName;
    private String patronymic;

    private String phoneNumber;
}
