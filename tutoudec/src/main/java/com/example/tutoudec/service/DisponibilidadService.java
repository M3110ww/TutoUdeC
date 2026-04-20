package com.example.tutoudec.service;

import com.example.tutoudec.model.Disponibilidad;
import com.example.tutoudec.model.Tutor;
import com.example.tutoudec.repository.DisponibilidadRepository;
import com.example.tutoudec.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;
    private final TutorRepository tutorRepository;

    public DisponibilidadService(DisponibilidadRepository disponibilidadRepository,
                                 TutorRepository tutorRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.tutorRepository = tutorRepository;
    }

    public List<Disponibilidad> listarPorTutor(Long tutorId) {
        return disponibilidadRepository.findByTutorId(tutorId);
    }

    public Disponibilidad agregar(Long tutorId, Disponibilidad disponibilidad) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado"));
        disponibilidad.setTutor(tutor);
        return disponibilidadRepository.save(disponibilidad);
    }

    public void eliminar(Long id) {
        disponibilidadRepository.deleteById(id);
    }
}