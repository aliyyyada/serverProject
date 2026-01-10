package com.example.demo.dto;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class StudentListItemResponse {
    private Long studentId;
    private Long userId;

    private String lastName;
    private String firstName;
    private String patronymic;

    private Integer hours;

    private Long instructorId;
    private String instructorName;

    private String kppType;


}
