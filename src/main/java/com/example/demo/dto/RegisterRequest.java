package com.example.demo.dto;
import lombok.Data;
@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String password;


    private String role;

    private String carPlate;
    private String carModel;
    private String carColor;
    private String typeKPP;
}
