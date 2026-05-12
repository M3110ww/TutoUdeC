package com.example.tutoudec.controller;
import com.example.tutoudec.dto.sesion.SesionRequest;
import com.example.tutoudec.dto.sesion.SesionResponse;
import com.example.tutoudec.service.SesionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SesionController {
    private final SesionService sesionService;
    public SesionController(SesionService sesionService) { this.sesionService = sesionService; }

    @GetMapping
    public ResponseEntity<List<SesionResponse>> getAll() { return ResponseEntity.ok(sesionService.getAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<SesionResponse> getById(@PathVariable Long id) { return ResponseEntity.ok(sesionService.findById(id)); }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<SesionResponse>> getByTutor(@PathVariable Long tutorId) { return ResponseEntity.ok(sesionService.findByTutor(tutorId)); }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<SesionResponse>> getByStudent(@PathVariable Long studentId) { return ResponseEntity.ok(sesionService.findByStudent(studentId)); }

    @PostMapping("/student/{studentId}")
    public ResponseEntity<SesionResponse> book(@PathVariable Long studentId, @Valid @RequestBody SesionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sesionService.book(studentId, request));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<SesionResponse> confirm(@PathVariable Long id) { return ResponseEntity.ok(sesionService.confirm(id)); }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<SesionResponse> cancel(@PathVariable Long id) { return ResponseEntity.ok(sesionService.cancel(id)); }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<SesionResponse> complete(@PathVariable Long id) { return ResponseEntity.ok(sesionService.complete(id)); }
}
