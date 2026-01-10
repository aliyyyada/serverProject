package com.example.demo.controller;

import com.example.demo.dto.InstructorShortResponse;
import com.example.demo.dto.StudentListItemResponse;
import com.example.demo.dto.UpdateStudentRequest;
import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/students")
    public List<StudentListItemResponse> getStudents() {
        return adminService.getAllStudents();
    }

    @GetMapping("/instructors")
    public List<InstructorShortResponse> getInstructors() {
        return adminService.getAllInstructors();
    }

    @PutMapping("/students/{id}")
    public void updateStudent(@PathVariable("id") Long studentId, @RequestBody UpdateStudentRequest req) {
        adminService.updateStudent(studentId, req);
    }
}
