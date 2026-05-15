package com.example.tutoudec.service;

import com.example.tutoudec.dto.student.StudentRequest;
import com.example.tutoudec.dto.student.StudentResponse;
import java.util.List;

public interface EstudianteService {
    StudentResponse registerStudent(Long userId, StudentRequest request);
    StudentResponse update(Long id, StudentRequest request);
    StudentResponse findById(Long id);
    List<StudentResponse> getAll();
}