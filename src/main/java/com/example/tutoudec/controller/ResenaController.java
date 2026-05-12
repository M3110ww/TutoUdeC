package com.example.tutoudec.controller;
import com.example.tutoudec.model.Resena;
import com.example.tutoudec.service.ResenaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ResenaController {
    private final ResenaService resenaService;
    public ResenaController(ResenaService resenaService) { this.resenaService = resenaService; }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Resena>> getByTutor(@PathVariable Long tutorId) { return ResponseEntity.ok(resenaService.findByTutor(tutorId)); }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Resena>> getByStudent(@PathVariable Long studentId) { return ResponseEntity.ok(resenaService.findByStudent(studentId)); }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Resena> getBySession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(resenaService.findBySession(sessionId)
                .orElseThrow(() -> new com.example.tutoudec.exception.ResourceNotFoundException("Review not found for session: " + sessionId)));
    }

    @PostMapping("/session/{sessionId}/student/{studentId}")
    public ResponseEntity<Resena> create(@PathVariable Long sessionId, @PathVariable Long studentId, @RequestBody Resena review) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.create(sessionId, studentId, review));
    }
}
