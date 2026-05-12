package com.example.tutoudec.controller;
import com.example.tutoudec.model.Disponibilidad;
import com.example.tutoudec.service.DisponibilidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class DisponibilidadController {
    private final DisponibilidadService disponibilidadService;
    public DisponibilidadController(DisponibilidadService disponibilidadService) { this.disponibilidadService = disponibilidadService; }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Disponibilidad>> getByTutor(@PathVariable Long tutorId) { return ResponseEntity.ok(disponibilidadService.findByTutor(tutorId)); }

    @GetMapping("/tutor/{tutorId}/available")
    public ResponseEntity<List<Disponibilidad>> getAvailable(@PathVariable Long tutorId) { return ResponseEntity.ok(disponibilidadService.findAvailableByTutor(tutorId)); }

    @PostMapping("/tutor/{tutorId}")
    public ResponseEntity<Disponibilidad> add(@PathVariable Long tutorId, @RequestBody Disponibilidad availability) {
        return ResponseEntity.status(HttpStatus.CREATED).body(disponibilidadService.add(tutorId, availability));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { disponibilidadService.delete(id); return ResponseEntity.noContent().build(); }
}
