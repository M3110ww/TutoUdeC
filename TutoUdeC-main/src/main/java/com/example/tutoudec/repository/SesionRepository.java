package com.example.tutoudec.repository;

import com.example.tutoudec.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    List<Sesion> findByTutorId(Long tutorId);
    List<Sesion> findByStudentId(Long studentId);
    List<Sesion> findByStatus(Sesion.Status status);
    List<Sesion> findByTutorIdAndStatus(Long tutorId, Sesion.Status status);
    Long countByStatus(Sesion.Status status);
}
