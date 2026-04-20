package com.example.tutoudec.repository;

import com.example.tutoudec.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    List<Tutor> findByActivoTrue();
    Optional<Tutor> findByUsuarioId(Long usuarioId);
    List<Tutor> findByEspecialidadContainingIgnoreCase(String especialidad);
}