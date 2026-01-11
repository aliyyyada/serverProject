package com.example.demo.repository;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findBySchedule(Schedule schedule);
    Optional<Booking> findByScheduleAndStatus(Schedule schedule, BookingStatus status);
}
