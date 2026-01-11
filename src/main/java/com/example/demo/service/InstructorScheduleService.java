package com.example.demo.service;

import com.example.demo.dto.CreateScheduleRequest;
import com.example.demo.dto.ScheduleSlotResponse;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.ScheduleStatus;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service @RequiredArgsConstructor
public class InstructorScheduleService {

    private final InstructorRepository instructorRepository;
    private final ScheduleRepository scheduleRepository;
    private final BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public List<ScheduleSlotResponse> getSlots(User user, LocalDate date){
        Instructor ins = instructorRepository.findByUserId(user.getId()).orElseThrow();
        return scheduleRepository
                .findByInstructorAndDateOrderByStartTime(ins, date)
                .stream()
                .map(this::toDto)
                .toList();

    }
    @Transactional
    public ScheduleSlotResponse create(User user, CreateScheduleRequest req) {
        Instructor ins = instructorRepository.findByUserId(user.getId())
                .orElseThrow();

        Schedule s = new Schedule(
                null,
                ins,
                req.getDate(),
                req.getStartTime(),
                req.getEndTime(),
                ScheduleStatus.free
        );

        return toDto(scheduleRepository.save(s));
    }
    @Transactional
    public ScheduleSlotResponse update(User user, Long id, CreateScheduleRequest req) {
        Schedule s = scheduleRepository.findById(id).orElseThrow();
        checkOwner(user, s);

        s.setDate(req.getDate());
        s.setStartTime(req.getStartTime());
        s.setEndTime(req.getEndTime());

        return toDto(s);
    }

    @Transactional
    public void delete(User user, Long id) {
        Schedule s = scheduleRepository.findById(id).orElseThrow();
        checkOwner(user, s);

        scheduleRepository.delete(s);
    }
    private void checkOwner(User user, Schedule s) {
        if (!s.getInstructor().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Not your slot");
        }
    }
    private ScheduleSlotResponse toDto(Schedule s) {
        var booking = bookingRepository.findBySchedule(s);

        String student = booking
                .map(b -> {
                    User u = b.getStudent().getUser();
                    return u.getLastName() + " " + u.getFirstName();
                })
                .orElse(null);

        return new ScheduleSlotResponse(
                s.getId(),
                s.getDate(),
                s.getStartTime(),
                s.getEndTime(),
                student,
                s.getStatus().name()
        );
    }


}
