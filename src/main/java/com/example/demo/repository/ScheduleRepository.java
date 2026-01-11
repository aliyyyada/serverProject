package com.example.demo.repository;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByInstructorIdAndDateBetween(Long instructorId, LocalDate from, LocalDate to);
    List<Schedule> findByStatus(String status);
    List<Schedule> findByInstructorAndDateOrderByStartTime(
            Instructor instructor,
            LocalDate date
    );
}
