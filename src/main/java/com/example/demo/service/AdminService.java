package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import javax.management.relation.Role;

@Service
public class AdminService {
    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void assignInstructor(Long studentId, Long instructorId){
        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Студент не найден."));
        User instructor = userRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("Инструктор не найден."));

        if (!student.getRole().equals("student")) {
            throw new RuntimeException("Данный пользователь не является студентом.");
        }
        if (!instructor.getRole().equals("instructor")){
            throw new RuntimeException("Данный пользователь не является инструктором.");
        }
        student.setInstructorId
    }

}
