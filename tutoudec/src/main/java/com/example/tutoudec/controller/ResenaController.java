package com.example.tutoudec.controller;

import com.example.tutoudec.model.Resena;
import com.example.tutoudec.service.ResenaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    // GET /api/resenas/tutor/{tutorId}
    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Resena>> porTutor(@PathVariable Long tutorId) {
        return ResponseEntity.ok(resenaService.listarPorTutor(tutorId));
    }

    // GET /api/resenas/estudiante/{estudianteId}
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Resena>> porEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(resenaService.listarPorEstudiante(estudianteId));
    }

    // GET /api/resenas/sesion/{sesionId}
    @GetMapping("/sesion/{sesionId}")
    public ResponseEntity<Resena> porSesion(@PathVariable Long sesionId) {
        return resenaService.buscarPorSesion(sesionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/resenas/sesion/{sesionId}/estudiante/{estudianteId}
    @PostMapping("/sesion/{sesionId}/estudiante/{estudianteId}")
    public ResponseEntity<?> crear(@PathVariable Long sesionId,
                                   @PathVariable Long estudianteId,
                                   @RequestBody Resena resena) {
        try {
            Resena nueva = resenaService.crear(sesionId, estudianteId, resena);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}