package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentListItemForInstructorResponse {
    private Long studentId;
    private Long userId;

    private String lastName;
    private String firstName;
    private String patronymic;
    private String phoneNumber;

    private Integer hours;

    private Long instructorId;
    private String instructorName;

    private String kppType;





}