package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="instructor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    private String carPlate;
    private String carModel;
    private String carColor;

    @Column(name="type_kpp")
    private String typeKPP;




}
