package com.example.demo.entity;
import com.example.demo.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="booking", uniqueConstraints = {
        @UniqueConstraint(name="uk_booking_schedule", columnNames = {"schedule_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="booking_id")
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="schedule_id", nullable=false)
    private Schedule schedule;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, length=20)
    private BookingStatus status; //booked, canceled

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
