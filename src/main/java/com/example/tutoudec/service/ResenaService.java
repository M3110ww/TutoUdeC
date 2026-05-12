package com.example.tutoudec.service;

import com.example.tutoudec.model.Resena;
import java.util.List;
import java.util.Optional;

public interface ResenaService {
    Resena create(Long sessionId, Long studentId, Resena review);
    List<Resena> findByTutor(Long tutorId);
    List<Resena> findByStudent(Long studentId);
    Optional<Resena> findBySession(Long sessionId);
}