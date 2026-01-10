package com.example.demo.entity;
import com.example.demo.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", nullable = false, length = 150)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 150)
    private String lastName;

    @Column(name="patronymic", length = 150)
    private String patronymic;

    @JsonIgnore
    @Column(name="password", nullable=false)
    private String password;

    @Column(name="phone_number", nullable=false, unique=true, length=11)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable=false, length=20)
    private Role role;// 'admin','student','instructor','user'


    @Column(name="is_active", nullable=false)
    private Boolean isActive = true;

    public void setActive(boolean active){
        this.isActive = active;
    }

}
