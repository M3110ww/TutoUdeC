package com.example.tutoudec.service;

import com.example.tutoudec.model.Tutor;
import com.example.tutoudec.model.Usuario;
import com.example.tutoudec.repository.TutorRepository;
import com.example.tutoudec.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TutorService {

    private final TutorRepository tutorRepository;
    private final UsuarioRepository usuarioRepository;

    public TutorService(TutorRepository tutorRepository,
                        UsuarioRepository usuarioRepository) {
        this.tutorRepository = tutorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Tutor> listarTodos() {
        return tutorRepository.findAll();
    }

    public List<Tutor> listarActivos() {
        return tutorRepository.findByActivoTrue();
    }

    public List<Tutor> buscarPorEspecialidad(String especialidad) {
        return tutorRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }

    public Optional<Tutor> buscarPorId(Long id) {
        return tutorRepository.findById(id);
    }

    public Tutor registrarTutor(Long usuarioId, Tutor tutor) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Usuario.Rol.TUTOR) {
            throw new RuntimeException("El usuario no tiene rol de TUTOR");
        }

        if (tutorRepository.findByUsuarioId(usuarioId).isPresent()) {
            throw new RuntimeException("Este usuario ya tiene perfil de tutor");
        }

        tutor.setUsuario(usuario);
        tutor.setActivo(true);
        tutor.setCalificacionPromedio(0.0);
        return tutorRepository.save(tutor);
    }

    public Tutor actualizar(Long id, Tutor datos) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado"));
        tutor.setEspecialidad(datos.getEspecialidad());
        tutor.setDescripcion(datos.getDescripcion());
        tutor.setTarifaHora(datos.getTarifaHora());
        return tutorRepository.save(tutor);
    }

    public Tutor cambiarEstado(Long id, Boolean activo) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado"));
        tutor.setActivo(activo);
        return tutorRepository.save(tutor);
    }
}