package com.example.tutoudec.repository;

import com.example.tutoudec.model.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {
    List<Disponibilidad> findByTutorId(Long tutorId);
    List<Disponibilidad> findByTutorIdAndDiaSemana(Long tutorId, Integer diaSemana);
}