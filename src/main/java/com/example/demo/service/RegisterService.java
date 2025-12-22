package com.example.demo.service;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Instructor;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.dto.RegisterRequest;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final InstructorRepository instructorRepository;

    public User registerSudent(RegisterRequest request){
        validatePhone(request.getPhoneNumber());

        User user = createUserBase(request);
        user.setRole("student");
        userRepository.save(user);
        Student st  = new Student();
        st.setUser(user);
        st.setHours(0);
        studentRepository.save(st);
        return user;
    }

    public User registerAdminOrInstructor(RegisterRequest request){
        validatePhone(request.getPhoneNumber());
        if (request.getRole()==null){
            throw new RuntimeException("Недопустимая роль.");

        }

        User user = createUserBase(request);
        user.setRole(request.getRole());
        userRepository.save(user);

        if (request.getRole().equals("admin")){
            Admin admin = new Admin();
            admin.setUser(user);
            adminRepository.save(admin);
        } else if (request.getRole().equals("instructor")) {
            Instructor ins = new Instructor();
            ins.setUser(user);
            instructorRepository.save(ins);
        }
        return user;

    }

    private void validatePhone(String phone){
        if (userRepository.findByPhoneNumber(phone).isPresent()){
            throw new RuntimeException("Номер уже используется.");
        }
    }

    private User createUserBase(RegisterRequest request){
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPatronymic(request.getPatronymic());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
        user.setActive(true);
        return user;
    }

}


