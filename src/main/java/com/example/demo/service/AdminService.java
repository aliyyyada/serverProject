package com.example.demo.service;

import com.example.demo.dto.InstructorShortResponse;
import com.example.demo.dto.StudentListItemResponse;
import com.example.demo.dto.UpdateStudentRequest;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;

    public List<StudentListItemResponse> getAllStudents() {
        return studentRepository.findAll().stream().map(st -> {
            User u = st.getUser();

            String instructorName = null;
            Long instructorId = null;
            if (st.getInstructor() != null && st.getInstructor().getUser() != null) {
                instructorId = st.getInstructor().getId();
                User iu = st.getInstructor().getUser();
                instructorName = iu.getLastName() + " " +
                        (iu.getFirstName().isEmpty() ? "" : iu.getFirstName().charAt(0) + ".") +
                        (iu.getPatronymic().isEmpty() ? "" : iu.getPatronymic().charAt(0) + ".");
            }

            return new StudentListItemResponse(
                    st.getId(),
                    u.getId(),
                    u.getLastName(),
                    u.getFirstName(),
                    u.getPatronymic(),
                    st.getHours(),
                    instructorId,
                    instructorName,
                    st.getTypeKpp()
            );
        }).toList();
    }

    public List<InstructorShortResponse> getAllInstructors() {
        return instructorRepository.findAll().stream().map(ins -> {
            User u = ins.getUser();
            String full = u.getLastName() + " " +
                    (u.getFirstName().isEmpty() ? "" : u.getFirstName().charAt(0) + ".") +
                    (u.getPatronymic().isEmpty() ? "" : u.getPatronymic().charAt(0) + ".");
            String kpp = (ins.getTypeKpp() == null) ? null : ins.getTypeKpp().toString();
            return new InstructorShortResponse(ins.getId(), full, kpp);

        }).toList();
    }

    @Transactional
    public void updateStudent(Long studentId, UpdateStudentRequest req) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        User user = student.getUser();
        user.setLastName(req.getLastName());
        user.setFirstName(req.getFirstName());
        user.setPatronymic(req.getPatronymic());
        userRepository.save(user);

        student.setHours(req.getHours() == null ? 0 : req.getHours());
        student.setTypeKpp(req.getKppType());

        if (req.getInstructorId() == null) {
            student.setInstructor(null);
        } else {
            Instructor instructor = instructorRepository.findById(req.getInstructorId())
                    .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
            student.setInstructor(instructor);
        }

        studentRepository.save(student);
    }

}
