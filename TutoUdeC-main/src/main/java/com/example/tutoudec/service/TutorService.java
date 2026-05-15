package com.example.tutoudec.service;

import com.example.tutoudec.dto.tutor.TutorRequest;
import com.example.tutoudec.dto.tutor.TutorResponse;
import java.util.List;

public interface TutorService {
    TutorResponse registerTutor(Long userId, TutorRequest request);
    TutorResponse update(Long id, TutorRequest request);
    TutorResponse findById(Long id);
    List<TutorResponse> getAll();
    List<TutorResponse> getActive();
    List<TutorResponse> searchBySpecialty(String specialty);
    TutorResponse changeStatus(Long id, Boolean active);
}