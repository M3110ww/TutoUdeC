package com.example.tutoudec.controller;

import com.example.tutoudec.model.Estudiante;
import com.example.tutoudec.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    // GET /api/estudiantes
    @GetMapping
    public ResponseEntity<List<Estudiante>> listar() {
        return ResponseEntity.ok(estudianteService.listarTodos());
    }

    // GET /api/estudiantes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtener(@PathVariable Long id) {
        return estudianteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/estudiantes/usuario/{usuarioId}
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> registrar(@PathVariable Long usuarioId,
                                       @RequestBody Estudiante estudiante) {
        try {
            Estudiante nuevo = estudianteService.registrarEstudiante(usuarioId, estudiante);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/estudiantes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody Estudiante estudiante) {
        try {
            return ResponseEntity.ok(estudianteService.actualizar(id, estudiante));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}