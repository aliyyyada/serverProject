package com.example.demo.dto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ScheduleSlotResponse {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String studentFullName;
    private String status;
}
