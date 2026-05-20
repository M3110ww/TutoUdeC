package com.example.tutoudec.controller;
import com.example.tutoudec.dto.tutor.TutorRequest;
import com.example.tutoudec.dto.tutor.TutorResponse;
import com.example.tutoudec.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {
    private final TutorService tutorService;
    public TutorController(TutorService tutorService) { this.tutorService = tutorService; }

    @GetMapping
    public ResponseEntity<List<TutorResponse>> getActive() { return ResponseEntity.ok(tutorService.getActive()); }

    @GetMapping("/all")
    public ResponseEntity<List<TutorResponse>> getAll() { return ResponseEntity.ok(tutorService.getAll()); }

    @GetMapping("/user/{userId}")
    public ResponseEntity<TutorResponse> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(tutorService.findByUserId(userId));
    }

    // AGREGAR en TutorService.java (interfaz):
    TutorResponse findByUserId(Long userId);

    // AGREGAR en TutorServiceImpl.java:
    @Override
    @Transactional(readOnly = true)
    public TutorResponse findByUserId(Long userId) {
        return tutorRepository.findByUserId(userId)
                .map(TutorResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tutor profile not found for userId: " + userId));
    }

// El repositorio TutorRepository ya tiene findByUserId — solo exponer el endpoint.


    @GetMapping("/user/{userId}")
    public ResponseEntity<TutorResponse> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(tutorService.findByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorResponse> getById(@PathVariable Long id) { return ResponseEntity.ok(tutorService.findById(id)); }

    @GetMapping("/search")
    public ResponseEntity<List<TutorResponse>> search(@RequestParam String specialty) { return ResponseEntity.ok(tutorService.searchBySpecialty(specialty)); }

    @PostMapping("/user/{userId}")
    public ResponseEntity<TutorResponse> register(@PathVariable Long userId, @Valid @RequestBody TutorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutorService.registerTutor(userId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorResponse> update(@PathVariable Long id, @Valid @RequestBody TutorRequest request) { return ResponseEntity.ok(tutorService.update(id, request)); }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TutorResponse> changeStatus(@PathVariable Long id, @RequestParam Boolean active) { return ResponseEntity.ok(tutorService.changeStatus(id, active)); }
}
