package com.example.tutoudec.controller;
import com.example.tutoudec.dto.student.StudentRequest;
import com.example.tutoudec.dto.student.StudentResponse;
import com.example.tutoudec.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class EstudianteController {
    private final EstudianteService estudianteService;
    public EstudianteController(EstudianteService estudianteService) { this.estudianteService = estudianteService; }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll() { return ResponseEntity.ok(estudianteService.getAll()); }

    @GetMapping("/user/{userId}")
    public ResponseEntity<StudentResponse> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(estudianteService.findByUserId(userId));
    }

    // AGREGAR en EstudianteService.java (interfaz):
    StudentResponse findByUserId(Long userId);

    // AGREGAR en EstudianteServiceImpl.java:
    @Override
    @Transactional(readOnly = true)
    public StudentResponse findByUserId(Long userId) {
        return estudianteRepository.findByUserId(userId)
                .map(StudentResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student profile not found for userId: " + userId));
    }

    // El repositorio EstudianteRepository ya tiene findByUserId — solo exponer el endpoint.
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id) { return ResponseEntity.ok(estudianteService.findById(id)); }

    @PostMapping("/user/{userId}")
    public ResponseEntity<StudentResponse> register(@PathVariable Long userId, @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.registerStudent(userId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) { return ResponseEntity.ok(estudianteService.update(id, request)); }
}
