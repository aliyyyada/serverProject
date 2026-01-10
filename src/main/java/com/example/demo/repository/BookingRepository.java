package com.example.demo.repository;
import com.example.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStudentIdAndStatus(Long studentId, String status);
    Optional<Booking> findByScheduleIdAndStudentIdAndStatus(Long scheduleId, Long studentId, String status);
}
