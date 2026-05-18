bro subelo tu para que sea mas easy @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                    // Swagger
                    .requestMatchers(
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/v3/api-docs",
                            "/v3/api-docs/**",
                            "/webjars/**"
                    ).permitAll()

                    // Auth
                    .requestMatchers("/api/auth/**").permitAll()

                    // Availability — students can GET, only tutors can POST/DELETE
                    .requestMatchers(HttpMethod.GET, "/api/availability/**").authenticated()
                    .requestMatchers(HttpMethod.POST, "/api/availability/**").hasAuthority("TUTOR")
                    .requestMatchers(HttpMethod.DELETE, "/api/availability/**").hasAuthority("TUTOR")

                    // Admin
                    .requestMatchers("/api/admin/**").hasAuthority("ADMIN")

                    // Everything else requires auth
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

// Este método es crítico — sin él el front recibe 403 por CORS
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(List.of("*"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setExposedHeaders(List.of("Authorization"));
    config.setAllowCredentials(false);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}