package com.example.tutoudec.service;

import com.example.tutoudec.config.JwtUtil;
import com.example.tutoudec.dto.auth.AuthResponse;
import com.example.tutoudec.dto.auth.LoginRequest;
import com.example.tutoudec.dto.auth.RegisterRequest;
import com.example.tutoudec.exception.BusinessException;
import com.example.tutoudec.exception.DuplicateResourceException;
import com.example.tutoudec.exception.ResourceNotFoundException;
import com.example.tutoudec.model.Usuario;
import com.example.tutoudec.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder,
                              JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already in use: " + request.getEmail());
        }

        Usuario user = new Usuario();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        Usuario saved = usuarioRepository.save(user);

        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole().name());
        return new AuthResponse(token, saved.getName(), saved.getRole(), saved.getId());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getName(), user.getRole(), user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario update(Long id, RegisterRequest request) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return usuarioRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}