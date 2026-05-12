package com.example.tutoudec.service;

import com.example.tutoudec.exception.BusinessException;
import com.example.tutoudec.exception.DuplicateResourceException;
import com.example.tutoudec.exception.ResourceNotFoundException;
import com.example.tutoudec.model.*;
import com.example.tutoudec.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResenaServiceImpl implements ResenaService {

    private final ResenaRepository resenaRepository;
    private final SesionRepository sesionRepository;
    private final EstudianteRepository estudianteRepository;
    private final TutorRepository tutorRepository;

    public ResenaServiceImpl(ResenaRepository resenaRepository,
                             SesionRepository sesionRepository,
                             EstudianteRepository estudianteRepository,
                             TutorRepository tutorRepository) {
        this.resenaRepository = resenaRepository;
        this.sesionRepository = sesionRepository;
        this.estudianteRepository = estudianteRepository;
        this.tutorRepository = tutorRepository;
    }

    @Override
    public Resena create(Long sessionId, Long studentId, Resena review) {
        Sesion session = sesionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with id: " + sessionId));

        // Only completed sessions can be reviewed
        if (session.getStatus() != Sesion.Status.COMPLETED) {
            throw new BusinessException("Only completed sessions can be reviewed");
        }

        // One review per session
        if (resenaRepository.findBySessionId(sessionId).isPresent()) {
            throw new DuplicateResourceException("A review already exists for session: " + sessionId);
        }

        Estudiante student = estudianteRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        // Rating must be 1-5
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new BusinessException("Rating must be between 1 and 5");
        }

        review.setSession(session);
        review.setStudent(student);
        Resena saved = resenaRepository.save(review);

        // Update tutor average rating
        updateTutorAverageRating(session.getTutor().getId());

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resena> findByTutor(Long tutorId) {
        return resenaRepository.findBySessionTutorId(tutorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Resena> findByStudent(Long studentId) {
        return resenaRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resena> findBySession(Long sessionId) {
        return resenaRepository.findBySessionId(sessionId);
    }

    // Recalculates and updates the tutor's average rating after a new review
    private void updateTutorAverageRating(Long tutorId) {
        List<Resena> reviews = resenaRepository.findBySessionTutorId(tutorId);
        double average = reviews.stream()
                .mapToInt(Resena::getRating)
                .average()
                .orElse(0.0);

        tutorRepository.findById(tutorId).ifPresent(tutor -> {
            tutor.setAverageRating(average);
            tutorRepository.save(tutor);
        });
    }
}
