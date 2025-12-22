package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name ="schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="instructor_id", nullable = false)
    private Instructor instructor;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private String status; //free, booked, canceled, completed



}
