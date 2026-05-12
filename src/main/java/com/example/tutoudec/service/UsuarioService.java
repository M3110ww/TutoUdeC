package com.example.tutoudec.service;

import com.example.tutoudec.dto.auth.AuthResponse;
import com.example.tutoudec.dto.auth.LoginRequest;
import com.example.tutoudec.dto.auth.RegisterRequest;
import com.example.tutoudec.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    List<Usuario> getAll();
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    Usuario update(Long id, RegisterRequest request);
    void delete(Long id);
}