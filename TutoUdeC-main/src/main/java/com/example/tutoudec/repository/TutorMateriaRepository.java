package com.example.tutoudec.repository;

import com.example.tutoudec.model.TutorMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TutorMateriaRepository extends JpaRepository<TutorMateria, Long> {
    List<TutorMateria> findByTutorId(Long tutorId);
    List<TutorMateria> findBySubjectId(Long subjectId);
}
