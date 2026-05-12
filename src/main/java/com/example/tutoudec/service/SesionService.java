package com.example.tutoudec.service;

import com.example.tutoudec.dto.sesion.SesionRequest;
import com.example.tutoudec.dto.sesion.SesionResponse;
import java.util.List;

public interface SesionService {
    SesionResponse book(Long studentId, SesionRequest request);
    SesionResponse confirm(Long id);
    SesionResponse cancel(Long id);
    SesionResponse complete(Long id);
    SesionResponse findById(Long id);
    List<SesionResponse> findByTutor(Long tutorId);
    List<SesionResponse> findByStudent(Long studentId);
    List<SesionResponse> getAll();
}