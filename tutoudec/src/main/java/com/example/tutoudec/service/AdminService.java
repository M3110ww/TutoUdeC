package com.example.tutoudec.service;

import com.example.tutoudec.model.Sesion;
import com.example.tutoudec.model.Tutor;
import com.example.tutoudec.model.Usuario;
import com.example.tutoudec.repository.SesionRepository;
import com.example.tutoudec.repository.TutorRepository;
import com.example.tutoudec.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class AdminService {

    private final SesionRepository sesionRepository;
    private final TutorRepository tutorRepository;
    private final UsuarioRepository usuarioRepository;

    public AdminService(SesionRepository sesionRepository,
                        TutorRepository tutorRepository,
                        UsuarioRepository usuarioRepository) {
        this.sesionRepository = sesionRepository;
        this.tutorRepository = tutorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Sesion> listarTodasLasSesiones() {
        return sesionRepository.findAll();
    }

    public List<Sesion> listarSesionesPorEstado(Sesion.Estado estado) {
        return sesionRepository.findByEstado(estado);
    }

    public Sesion confirmarSesion(Long id) {
        Sesion sesion = sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        sesion.setEstado(Sesion.Estado.CONFIRMADA);
        return sesionRepository.save(sesion);
    }

    public Sesion cancelarSesion(Long id) {
        Sesion sesion = sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        sesion.setEstado(Sesion.Estado.CANCELADA);
        return sesionRepository.save(sesion);
    }

    public List<Tutor> listarTodosTutores() {
        return tutorRepository.findAll();
    }

    public Tutor cambiarEstadoTutor(Long id, Boolean activo) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado"));
        tutor.setActivo(activo);
        return tutorRepository.save(tutor);
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public long contarSesionesPorEstado(Sesion.Estado estado) {
        return sesionRepository.findByEstado(estado).size();
    }
}