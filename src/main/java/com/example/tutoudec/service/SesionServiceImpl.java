package com.example.tutoudec.service;

import com.example.tutoudec.dto.sesion.SesionRequest;
import com.example.tutoudec.dto.sesion.SesionResponse;
import com.example.tutoudec.exception.BusinessException;
import com.example.tutoudec.exception.ResourceNotFoundException;
import com.example.tutoudec.model.*;
import com.example.tutoudec.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SesionServiceImpl implements SesionService {

    private final SesionRepository sesionRepository;
    private final TutorRepository tutorRepository;
    private final EstudianteRepository estudianteRepository;

    public SesionServiceImpl(SesionRepository sesionRepository,
                             TutorRepository tutorRepository,
                             EstudianteRepository estudianteRepository) {
        this.sesionRepository = sesionRepository;
        this.tutorRepository = tutorRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public SesionResponse book(Long studentId, SesionRequest request) {
        Tutor tutor = tutorRepository.findById(request.getTutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with id: " + request.getTutorId()));

        // Tutor must be active to receive sessions
        if (!tutor.getActive()) {
            throw new BusinessException("Tutor is not active and cannot receive sessions");
        }

        Estudiante student = estudianteRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        // Calculate total cost: hourlyRate * (durationMinutes / 60)
        double totalCost = tutor.getHourlyRate() * (request.getDurationMinutes() / 60.0);

        Sesion session = new Sesion();
        session.setTutor(tutor);
        session.setStudent(student);
        session.setScheduledAt(request.getScheduledAt());
        session.setDurationMinutes(request.getDurationMinutes());
        session.setTotalCost(totalCost);
        session.setModality(request.getModality());
        session.setMeetingLink(request.getMeetingLink());
        session.setStatus(Sesion.Status.PENDING);

        return new SesionResponse(sesionRepository.save(session));
    }

    @Override
    public SesionResponse confirm(Long id) {
        Sesion session = getSessionOrThrow(id);
        validatePending(session);
        session.setStatus(Sesion.Status.CONFIRMED);
        return new SesionResponse(sesionRepository.save(session));
    }

    @Override
    public SesionResponse cancel(Long id) {
        Sesion session = getSessionOrThrow(id);
        validatePending(session);
        session.setStatus(Sesion.Status.CANCELLED);
        return new SesionResponse(sesionRepository.save(session));
    }

    @Override
    public SesionResponse complete(Long id) {
        Sesion session = getSessionOrThrow(id);
        if (session.getStatus() != Sesion.Status.CONFIRMED) {
            throw new BusinessException("Only confirmed sessions can be marked as completed");
        }
        session.setStatus(Sesion.Status.COMPLETED);
        return new SesionResponse(sesionRepository.save(session));
    }

    @Override
    @Transactional(readOnly = true)
    public SesionResponse findById(Long id) {
        return new SesionResponse(getSessionOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionResponse> findByTutor(Long tutorId) {
        return sesionRepository.findByTutorId(tutorId)
                .stream().map(SesionResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionResponse> findByStudent(Long studentId) {
        return sesionRepository.findByStudentId(studentId)
                .stream().map(SesionResponse::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionResponse> getAll() {
        return sesionRepository.findAll()
                .stream().map(SesionResponse::new).collect(Collectors.toList());
    }

    // ── Private helpers ───────────────────────────────────────────
    private Sesion getSessionOrThrow(Long id) {
        return sesionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + id));
    }

    private void validatePending(Sesion session) {
        if (session.getStatus() != Sesion.Status.PENDING) {
            throw new BusinessException("Session has already been processed. Current status: " + session.getStatus());
        }
    }
}