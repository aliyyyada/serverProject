package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="auth_token")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AuthToken {
    @Id
    @Column(name="token", length=64)
    private String token;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="expires_at")
    private LocalDateTime expiresAt;
}
