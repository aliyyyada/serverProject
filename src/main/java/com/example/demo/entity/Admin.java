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
    @Column(name="admin_id")
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

}
