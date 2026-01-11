package com.example.demo.controller;

import com.example.demo.dto.InstructorMeResponse;
import com.example.demo.dto.StudentListItemForInstructorResponse;
import com.example.demo.dto.UpdateInstructorCarRequest;
import com.example.demo.entity.User;
import com.example.demo.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructor")
public class InstructorContoller {
    private final InstructorService instructorService;

    @GetMapping("/students")
    public List<StudentListItemForInstructorResponse> myStudents(@AuthenticationPrincipal User user) {
        return instructorService.getMyStudents(user);
    }
    @PutMapping("/car")
    public InstructorMeResponse updateCar(@AuthenticationPrincipal User user, @RequestBody UpdateInstructorCarRequest req) {
        return instructorService.updateMyCar(user, req);
    }
    @GetMapping("/me")
    public InstructorMeResponse me(@AuthenticationPrincipal User user) {
        return instructorService.getMyProfile(user);
    }
}
