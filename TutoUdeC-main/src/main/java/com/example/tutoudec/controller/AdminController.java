package com.example.tutoudec.controller;
import com.example.tutoudec.dto.sesion.SesionResponse;
import com.example.tutoudec.dto.tutor.TutorResponse;
import com.example.tutoudec.model.Sesion;
import com.example.tutoudec.model.Usuario;
import com.example.tutoudec.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService) { this.adminService = adminService; }

    @GetMapping("/sessions")
    public ResponseEntity<List<SesionResponse>> getAllSessions() { return ResponseEntity.ok(adminService.getAllSessions()); }

    @GetMapping("/sessions/status/{status}")
    public ResponseEntity<List<SesionResponse>> getByStatus(@PathVariable Sesion.Status status) { return ResponseEntity.ok(adminService.getSessionsByStatus(status)); }

    @PatchMapping("/sessions/{id}/confirm")
    public ResponseEntity<SesionResponse> confirmSession(@PathVariable Long id) { return ResponseEntity.ok(adminService.confirmSession(id)); }

    @PatchMapping("/sessions/{id}/cancel")
    public ResponseEntity<SesionResponse> cancelSession(@PathVariable Long id) { return ResponseEntity.ok(adminService.cancelSession(id)); }

    @GetMapping("/tutors")
    public ResponseEntity<List<TutorResponse>> getAllTutors() { return ResponseEntity.ok(adminService.getAllTutors()); }

    @PatchMapping("/tutors/{id}/status")
    public ResponseEntity<TutorResponse> changeTutorStatus(@PathVariable Long id, @RequestParam Boolean active) { return ResponseEntity.ok(adminService.changeTutorStatus(id, active)); }

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getAllUsers() { return ResponseEntity.ok(adminService.getAllUsers()); }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() { return ResponseEntity.ok(adminService.getSessionStats()); }
}
