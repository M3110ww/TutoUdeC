package com.example.tutoudec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    // Inyecta tu jwtFilter aquí
    // private final JwtFilter jwtFilter;
    //
    // public SecurityConfig(JwtFilter jwtFilter) {
    //     this.jwtFilter = jwtFilter;
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // Permitir preflight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Swagger
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()

                        // Auth pública
                        .requestMatchers("/api/auth/**").permitAll()

                        // =========================
                        // AVAILABILITY
                        // =========================

                        // GET -> STUDENT, TUTOR, ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/availability/**")
                        .hasAnyAuthority(
                                "STUDENT",
                                "ROLE_STUDENT",
                                "TUTOR",
                                "ROLE_TUTOR",
                                "ADMIN",
                                "ROLE_ADMIN"
                        )

                        // POST -> TUTOR, ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/availability/**")
                        .hasAnyAuthority(
                                "TUTOR",
                                "ROLE_TUTOR",
                                "ADMIN",
                                "ROLE_ADMIN"
                        )

                        // DELETE -> TUTOR, ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/api/availability/**")
                        .hasAnyAuthority(
                                "TUTOR",
                                "ROLE_TUTOR",
                                "ADMIN",
                                "ROLE_ADMIN"
                        )

                        // ADMIN
                        .requestMatchers("/api/admin/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // Cualquier otra request requiere auth
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("*"));

        config.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
        ));

        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With"
        ));

        config.setExposedHeaders(List.of("Authorization"));

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}