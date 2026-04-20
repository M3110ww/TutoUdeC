package com.example.tutoudec.controller;

import com.example.tutoudec.model.Disponibilidad;
import com.example.tutoudec.service.DisponibilidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    public DisponibilidadController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

    // GET /api/disponibilidad/tutor/{tutorId}
    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Disponibilidad>> porTutor(@PathVariable Long tutorId) {
        return ResponseEntity.ok(disponibilidadService.listarPorTutor(tutorId));
    }

    // POST /api/disponibilidad/tutor/{tutorId}
    @PostMapping("/tutor/{tutorId}")
    public ResponseEntity<?> agregar(@PathVariable Long tutorId,
                                     @RequestBody Disponibilidad disponibilidad) {
        try {
            Disponibilidad nueva = disponibilidadService.agregar(tutorId, disponibilidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/disponibilidad/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        disponibilidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}