package com.example.tutoudec.service;

import com.example.tutoudec.model.Estudiante;
import com.example.tutoudec.model.Usuario;
import com.example.tutoudec.repository.EstudianteRepository;
import com.example.tutoudec.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;

    public EstudianteService(EstudianteRepository estudianteRepository,
                             UsuarioRepository usuarioRepository) {
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> buscarPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    public Estudiante registrarEstudiante(Long usuarioId, Estudiante estudiante) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Usuario.Rol.ESTUDIANTE) {
            throw new RuntimeException("El usuario no tiene rol de ESTUDIANTE");
        }

        if (estudianteRepository.findByUsuarioId(usuarioId).isPresent()) {
            throw new RuntimeException("Este usuario ya tiene perfil de estudiante");
        }

        estudiante.setUsuario(usuario);
        return estudianteRepository.save(estudiante);
    }

    public Estudiante actualizar(Long id, Estudiante datos) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        estudiante.setNivelAcademico(datos.getNivelAcademico());
        estudiante.setIntereses(datos.getIntereses());
        return estudianteRepository.save(estudiante);
    }
}