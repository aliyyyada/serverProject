package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false, unique=true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="instructor_id")
    private Instructor instructor;

    @Column(name="hours", nullable=false)
    private Integer hours = 0;

    @Column(name="type_kpp", nullable = false)
    private String typeKpp; //mkpp, akpp

}
