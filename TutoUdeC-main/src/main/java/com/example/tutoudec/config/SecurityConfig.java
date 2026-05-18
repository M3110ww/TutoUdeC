package com.example.tutoudec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permisos para Swagger
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll()

                        // Permisos para Auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // --- CORRECCIÓN AQUÍ: Acceso a Disponibilidad ---
                        // Cualquier usuario autenticado (Estudiante o Tutor) puede ver los horarios (GET)
                        .requestMatchers(HttpMethod.GET, "/api/availability/**").authenticated()

                        // Solo los tutores pueden crear o borrar disponibilidad
                        .requestMatchers(HttpMethod.POST, "/api/availability/**").hasAuthority("TUTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/availability/**").hasAuthority("TUTOR")

                        // Todo lo demás requiere login
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}