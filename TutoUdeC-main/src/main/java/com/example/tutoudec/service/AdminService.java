package com.example.tutoudec.service;

import com.example.tutoudec.dto.sesion.SesionResponse;
import com.example.tutoudec.dto.tutor.TutorResponse;
import com.example.tutoudec.model.Sesion;
import com.example.tutoudec.model.Usuario;
import java.util.List;
import java.util.Map;

public interface AdminService {
    List<SesionResponse> getAllSessions();
    List<SesionResponse> getSessionsByStatus(Sesion.Status status);
    SesionResponse confirmSession(Long id);
    SesionResponse cancelSession(Long id);
    List<TutorResponse> getAllTutors();
    TutorResponse changeTutorStatus(Long id, Boolean active);
    List<Usuario> getAllUsers();
    Map<String, Long> getSessionStats();
}