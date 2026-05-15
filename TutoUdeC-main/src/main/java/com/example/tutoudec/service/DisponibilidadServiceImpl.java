package com.example.tutoudec.service;

import com.example.tutoudec.exception.BusinessException;
import com.example.tutoudec.exception.ResourceNotFoundException;
import com.example.tutoudec.model.Disponibilidad;
import com.example.tutoudec.model.Tutor;
import com.example.tutoudec.repository.DisponibilidadRepository;
import com.example.tutoudec.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DisponibilidadServiceImpl implements DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;
    private final TutorRepository tutorRepository;

    public DisponibilidadServiceImpl(DisponibilidadRepository disponibilidadRepository,
                                     TutorRepository tutorRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.tutorRepository = tutorRepository;
    }

    @Override
    public Disponibilidad add(Long tutorId, Disponibilidad availability) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with id: " + tutorId));

        // Check for overlapping blocks on the same day
        List<Disponibilidad> existing = disponibilidadRepository
                .findByTutorIdAndDayOfWeek(tutorId, availability.getDayOfWeek());

        boolean overlaps = existing.stream().anyMatch(e ->
                availability.getStartTime().isBefore(e.getEndTime()) &&
                        availability.getEndTime().isAfter(e.getStartTime())
        );

        if (overlaps) {
            throw new BusinessException("The availability block overlaps with an existing one");
        }

        availability.setTutor(tutor);
        availability.setOccupied(false);
        return disponibilidadRepository.save(availability);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Disponibilidad> findByTutor(Long tutorId) {
        return disponibilidadRepository.findByTutorId(tutorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Disponibilidad> findAvailableByTutor(Long tutorId) {
        return disponibilidadRepository.findByTutorIdAndOccupiedFalse(tutorId);
    }

    @Override
    public void delete(Long id) {
        Disponibilidad availability = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Availability block not found with id: " + id));

        // Cannot delete an occupied block (has an active session)
        if (availability.getOccupied()) {
            throw new BusinessException("Cannot delete an occupied availability block");
        }

        disponibilidadRepository.deleteById(id);
    }
}
