package com.example.demo.repository;

import com.example.demo.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
    Optional<AuthToken> findByToken(String token);
    void deleteByToken(String token);
}
