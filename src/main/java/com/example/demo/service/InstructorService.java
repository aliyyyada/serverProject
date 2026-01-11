package com.example.demo.service;

import com.example.demo.dto.InstructorMeResponse;
import com.example.demo.dto.StudentListItemForInstructorResponse;
import com.example.demo.dto.UpdateInstructorCarRequest;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public List<StudentListItemForInstructorResponse> getMyStudents(User currentUser) {

        Instructor instructor = instructorRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Instructor profile not found for userId=" + currentUser.getId()));

        String instructorName = instructorShortName(instructor.getUser());

        return studentRepository.findByInstructorId(instructor.getId())
                .stream()
                .map(st -> toDto(st, instructor.getId(), instructorName))
                .toList();
    }

    private StudentListItemForInstructorResponse toDto(Student st, Long instructorId, String instructorName) {
        User u = st.getUser();
        return new StudentListItemForInstructorResponse(
                st.getId(),
                u.getId(),
                u.getLastName(),
                u.getFirstName(),
                u.getPatronymic(),
                u.getPhoneNumber(),

                st.getHours(),
                instructorId,
                instructorName,
                st.getTypeKpp()

        );
    }

    private String instructorShortName(User u) {
        if (u == null) return null;
        String f = (u.getFirstName() == null || u.getFirstName().isEmpty()) ? "" : (u.getFirstName().charAt(0) + ".");
        String p = (u.getPatronymic() == null || u.getPatronymic().isEmpty()) ? "" : (u.getPatronymic().charAt(0) + ".");
        return u.getLastName() + " " + f + p;
    }

    @Transactional(readOnly = true)
    public InstructorMeResponse getMyProfile(User currentUser) {
        Instructor ins = instructorRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Instructor profile not found for userId=" + currentUser.getId()));

        User u = ins.getUser();
        String kpp = ins.getTypeKpp() == null ? null : ins.getTypeKpp().toString();

        return new InstructorMeResponse(
                ins.getId(),
                u.getId(),
                u.getLastName(),
                u.getFirstName(),
                u.getPatronymic(),
                u.getPhoneNumber(),
                ins.getCarModel(),
                ins.getCarColor(),
                ins.getCarPlate(),
                kpp
        );
    }
    @Transactional
    public InstructorMeResponse updateMyCar(User currentUser, UpdateInstructorCarRequest req) {
        Instructor ins = instructorRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Instructor profile not found for userId=" + currentUser.getId()));

        ins.setCarModel(req.getCarModel());
        ins.setCarColor(req.getCarColor());
        ins.setCarPlate(req.getCarPlate());

        instructorRepository.save(ins);


        return getMyProfile(currentUser);
    }
}
