package com.example.demo.entity;
import com.example.demo.entity.enums.ScheduleStatus;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="instructor_id", nullable=false)
    private Instructor instructor;

    @Column(name="date", nullable=false)
    private LocalDate date;

    @Column(name="start_time", nullable=false)
    private LocalTime startTime;

    @Column(name="end_time", nullable=false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, length=20)
    private ScheduleStatus status; //free, booked, canceled


}
