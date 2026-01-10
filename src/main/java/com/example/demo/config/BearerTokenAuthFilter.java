package com.example.demo.config;

import com.example.demo.entity.AuthToken;
import com.example.demo.entity.User;
import com.example.demo.repository.AuthTokenRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor

public class BearerTokenAuthFilter extends OncePerRequestFilter {
    private final AuthTokenRepository tokenRepository;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenValue = header.substring("Bearer ".length()).trim();
        if (tokenValue.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        AuthToken token = tokenRepository.findById(tokenValue).orElse(null);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (token.getExpiresAt() != null && token.getExpiresAt().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(token);
            filterChain.doFilter(request, response);
            return;
        }
        User user = token.getUser();
/*
        User user = userRepository.findById(token.getUser().getId()).orElse(null);
        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }
*/
        String role = user.getRole().toString().toUpperCase();
        var auth = new UsernamePasswordAuthenticationToken(
                user,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
