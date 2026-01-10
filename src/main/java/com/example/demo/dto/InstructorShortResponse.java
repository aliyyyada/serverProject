package com.example.demo.dto;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class InstructorShortResponse {
    private Long instructorId;
    private String fullName;
    private String kppType;
}
