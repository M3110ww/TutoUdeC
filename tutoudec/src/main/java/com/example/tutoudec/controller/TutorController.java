package com.example.tutoudec.controller;

import com.example.tutoudec.model.Tutor;
import com.example.tutoudec.service.TutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tutores")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    // GET /api/tutores
    @GetMapping
    public ResponseEntity<List<Tutor>> listar() {
        return ResponseEntity.ok(tutorService.listarActivos());
    }

    // GET /api/tutores/todos
    @GetMapping("/todos")
    public ResponseEntity<List<Tutor>> listarTodos() {
        return ResponseEntity.ok(tutorService.listarTodos());
    }

    // GET /api/tutores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Tutor> obtener(@PathVariable Long id) {
        return tutorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/tutores/buscar?especialidad=matematicas
    @GetMapping("/buscar")
    public ResponseEntity<List<Tutor>> buscarPorEspecialidad(
            @RequestParam String especialidad) {
        return ResponseEntity.ok(tutorService.buscarPorEspecialidad(especialidad));
    }

    // POST /api/tutores/usuario/{usuarioId}
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> registrar(@PathVariable Long usuarioId,
                                       @RequestBody Tutor tutor) {
        try {
            Tutor nuevo = tutorService.registrarTutor(usuarioId, tutor);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/tutores/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody Tutor tutor) {
        try {
            return ResponseEntity.ok(tutorService.actualizar(id, tutor));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH /api/tutores/{id}/estado?activo=false
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id,
                                           @RequestParam Boolean activo) {
        try {
            return ResponseEntity.ok(tutorService.cambiarEstado(id, activo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}