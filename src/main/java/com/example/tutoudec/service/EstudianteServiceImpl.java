package com.example.tutoudec.service;

import com.example.tutoudec.dto.student.StudentRequest;
import com.example.tutoudec.dto.student.StudentResponse;
import com.example.tutoudec.exception.DuplicateResourceException;
import com.example.tutoudec.exception.ResourceNotFoundException;
import com.example.tutoudec.model.Estudiante;
import com.example.tutoudec.model.Usuario;
import com.example.tutoudec.repository.EstudianteRepository;
import com.example.tutoudec.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository,
                                 UsuarioRepository usuarioRepository) {
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public StudentResponse registerStudent(Long userId, StudentRequest request) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (estudianteRepository.existsByUserId(userId)) {
            throw new DuplicateResourceException("Student profile already exists for user: " + userId);
        }

        Estudiante student = new Estudiante();
        student.setUser(user);
        student.setAcademicLevel(request.getAcademicLevel());
        student.setInterests(request.getInterest());

        return new StudentResponse(estudianteRepository.save(student));
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {
        Estudiante student = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        student.setAcademicLevel(request.getAcademicLevel());
        student.setInterests(request.getInterest());

        return new StudentResponse(estudianteRepository.save(student));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse findById(Long id) {
        return estudianteRepository.findById(id)
                .map(StudentResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAll() {
        return estudianteRepository.findAll()
                .stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }
}
