package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="admin")
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

}
