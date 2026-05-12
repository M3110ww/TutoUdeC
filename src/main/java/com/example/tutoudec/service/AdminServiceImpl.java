package com.example.tutoudec.service;

import com.example.tutoudec.dto.sesion.SesionResponse;
import com.example.tutoudec.dto.tutor.TutorResponse;
import com.example.tutoudec.exception.ResourceNotFoundException;
import com.example.tutoudec.model.*;
import com.example.tutoudec.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final SesionRepository sesionRepository;
    private final TutorRepository tutorRepository;
    private final UsuarioRepository usuarioRepository;

    public AdminServiceImpl(SesionRepository sesionRepository,
                            TutorRepository tutorRepository,
                            UsuarioRepository usuarioRepository) {
        this.sesionRepository = sesionRepository;
        this.tutorRepository = tutorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionResponse> getAllSessions() {
        return sesionRepository.findAll()
                .stream().map(SesionResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionResponse> getSessionsByStatus(Sesion.Status status) {
        return sesionRepository.findByStatus(status)
                .stream().map(SesionResponse::new).collect(Collectors.toList());
    }

    @Override
    public SesionResponse confirmSession(Long id) {
        Sesion session = sesionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + id));
        session.setStatus(Sesion.Status.CONFIRMED);
        return new SesionResponse(sesionRepository.save(session));
    }

    @Override
    public SesionResponse cancelSession(Long id) {
        Sesion session = sesionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + id));
        session.setStatus(Sesion.Status.CANCELLED);
        return new SesionResponse(sesionRepository.save(session));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorResponse> getAllTutors() {
        return tutorRepository.findAll()
                .stream().map(TutorResponse::new).collect(Collectors.toList());
    }

    @Override
    public TutorResponse changeTutorStatus(Long id, Boolean active) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with id: " + id));
        tutor.setActive(active);
        return new TutorResponse(tutorRepository.save(tutor));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getSessionStats() {
        return Map.of(
                "PENDING",   sesionRepository.countByStatus(Sesion.Status.PENDING),
                "CONFIRMED", sesionRepository.countByStatus(Sesion.Status.CONFIRMED),
                "CANCELLED", sesionRepository.countByStatus(Sesion.Status.CANCELLED),
                "COMPLETED", sesionRepository.countByStatus(Sesion.Status.COMPLETED)
        );
    }
}
