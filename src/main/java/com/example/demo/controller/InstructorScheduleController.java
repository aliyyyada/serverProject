package com.example.demo.controller;

import com.example.demo.dto.CreateScheduleRequest;
import com.example.demo.dto.ScheduleSlotResponse;
import com.example.demo.entity.User;
import com.example.demo.service.InstructorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructor/schedule")
public class InstructorScheduleController {
    private final InstructorScheduleService service;

    @GetMapping
    public List<ScheduleSlotResponse> byDate(
            @AuthenticationPrincipal User user,
            @RequestParam LocalDate date
    ) {
        return service.getSlots(user, date);
    }

    @PostMapping
    public ScheduleSlotResponse create(
            @AuthenticationPrincipal User user,
            @RequestBody CreateScheduleRequest req
    ) {
        return service.create(user, req);
    }

    @PutMapping("/{id}")
    public ScheduleSlotResponse update(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody CreateScheduleRequest req
    ) {
        return service.update(user, id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        service.delete(user, id);
    }
}
