package com.example.demo.dto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CreateScheduleRequest {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
