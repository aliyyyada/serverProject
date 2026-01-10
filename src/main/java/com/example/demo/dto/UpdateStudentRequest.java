package com.example.demo.dto;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UpdateStudentRequest {
    private String lastName;
    private String firstName;
    private String patronymic;
    private Integer hours;

    private Long instructorId;
    private String kppType;

}
