package com.example.demo.dto;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UpdateInstructorCarRequest {
    private String carModel;
    private String carColor;
    private String carPlate;
}
