package com.example.tutoudec.service;

import com.example.tutoudec.model.Disponibilidad;
import java.util.List;

public interface DisponibilidadService {
    Disponibilidad add(Long tutorId, Disponibilidad availability);
    List<Disponibilidad> findByTutor(Long tutorId);
    List<Disponibilidad> findAvailableByTutor(Long tutorId);
    void delete(Long id);
}