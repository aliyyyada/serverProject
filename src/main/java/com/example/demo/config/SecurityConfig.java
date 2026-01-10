package com.example.demo.config;


import com.example.demo.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthTokenRepository tokenRepository;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // login/register
                        //.requestMatchers("/api/admin/**").hasRole("admin")
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/instructor/**").hasAuthority("ROLE_INSTRUCTOR")

                        .anyRequest().authenticated()
                )
                //.httpBasic(Customizer.withDefaults());
                .addFilterBefore(
                        new BearerTokenAuthFilter(tokenRepository),
                        UsernamePasswordAuthenticationFilter.class
                );



        return http.build();
    }
}
