package com.example.tutoudec.repository;

import com.example.tutoudec.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByEstudianteId(Long estudianteId);
    Optional<Resena> findBySesionId(Long sesionId);
    List<Resena> findBySesionTutorId(Long tutorId);
}