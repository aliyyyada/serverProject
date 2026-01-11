package com.example.demo.dto;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class InstructorMeResponse {
    private Long instructorId;

    private Long userId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String phoneNumber;

    private String carModel;
    private String carColor;
    private String carPlate;

    private String typeKpp;
}
