package com.example.demo.entity;
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

    @Column(name="patronymic", nullable = false, length = 150)
    private String patronymic;

    @Column(nullable = false)
    private String password;

    @Column(name="phone_number", nullable=false, unique=true)
    private String phoneNumber;

    @Column(nullable=false)
    private String role; // 'admin','student','instructor','user'

    @Column(name="is_active")
    private Boolean isActive = true;

    public void setActive(boolean active){
        this.isActive = active;
    }

}
