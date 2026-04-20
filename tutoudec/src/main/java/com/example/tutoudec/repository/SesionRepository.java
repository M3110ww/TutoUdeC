package com.example.tutoudec.repository;

import com.example.tutoudec.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByTutorId(Long tutorId);
    List<Sesion> findByEstudianteId(Long estudianteId);
    List<Sesion> findByEstado(Sesion.Estado estado);
    List<Sesion> findByTutorIdAndEstado(Long tutorId, Sesion.Estado estado);
}