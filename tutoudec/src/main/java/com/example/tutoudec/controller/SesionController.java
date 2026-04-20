package com.example.tutoudec.controller;

import com.example.tutoudec.model.Sesion;
import com.example.tutoudec.service.SesionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
public class SesionController {

    private final SesionService sesionService;

    public SesionController(SesionService sesionService) {
        this.sesionService = sesionService;
    }

    // GET /api/sesiones
    @GetMapping
    public ResponseEntity<List<Sesion>> listar() {
        return ResponseEntity.ok(sesionService.listarTodas());
    }

    // GET /api/sesiones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Sesion> obtener(@PathVariable Long id) {
        return sesionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/sesiones/tutor/{tutorId}
    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Sesion>> porTutor(@PathVariable Long tutorId) {
        return ResponseEntity.ok(sesionService.listarPorTutor(tutorId));
    }

    // GET /api/sesiones/estudiante/{estudianteId}
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Sesion>> porEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(sesionService.listarPorEstudiante(estudianteId));
    }

    // GET /api/sesiones/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Sesion>> porEstado(@PathVariable Sesion.Estado estado) {
        return ResponseEntity.ok(sesionService.listarPorEstado(estado));
    }

    // POST /api/sesiones/tutor/{tutorId}/estudiante/{estudianteId}
    @PostMapping("/tutor/{tutorId}/estudiante/{estudianteId}")
    public ResponseEntity<?> reservar(@PathVariable Long tutorId,
                                      @PathVariable Long estudianteId,
                                      @RequestBody Sesion sesion) {
        try {
            Sesion nueva = sesionService.reservar(tutorId, estudianteId, sesion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH /api/sesiones/{id}/confirmar
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(sesionService.confirmar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH /api/sesiones/{id}/cancelar
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(sesionService.cancelar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH /api/sesiones/{id}/completar
    @PatchMapping("/{id}/completar")
    public ResponseEntity<?> completar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(sesionService.completar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}