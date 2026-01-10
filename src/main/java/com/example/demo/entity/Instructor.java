package com.example.demo.entity;
import com.example.demo.entity.enums.KppType;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="instructor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="instructor_id")
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false, unique=true)

    private User user;

    @Column(name="car_plate", length=50)
    private String carPlate;

    @Column(name="car_model", length=50)
    private String carModel;

    @Column(name="car_color", length=50)
    private String carColor;

    @Enumerated(EnumType.STRING)
    @Column(name="type_kpp", length=10)
    private KppType typeKpp;
}