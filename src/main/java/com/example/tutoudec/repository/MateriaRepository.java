package com.example.tutoudec.repository;

import com.example.tutoudec.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    Optional<Materia> findByName(String name);
    boolean existsByName(String name);
    List<Materia> findByCategory(String category);
}