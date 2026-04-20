package com.example.tutoudec.controller;

import com.example.tutoudec.model.Sesion;
import com.example.tutoudec.model.Tutor;
import com.example.tutoudec.model.Usuario;
import com.example.tutoudec.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // GET /api/admin/sesiones
    @GetMapping("/sesiones")
    public ResponseEntity<List<Sesion>> todasLasSesiones() {
        return ResponseEntity.ok(adminService.listarTodasLasSesiones());
    }

    // GET /api/admin/sesiones/estado/{estado}
    @GetMapping("/sesiones/estado/{estado}")
    public ResponseEntity<List<Sesion>> sesionesPorEstado(
            @PathVariable Sesion.Estado estado) {
        return ResponseEntity.ok(adminService.listarSesionesPorEstado(estado));
    }

    // PATCH /api/admin/sesiones/{id}/confirmar
    @PatchMapping("/sesiones/{id}/confirmar")
    public ResponseEntity<?> confirmarSesion(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.confirmarSesion(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH /api/admin/sesiones/{id}/cancelar
    @PatchMapping("/sesiones/{id}/cancelar")
    public ResponseEntity<?> cancelarSesion(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.cancelarSesion(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/admin/tutores
    @GetMapping("/tutores")
    public ResponseEntity<List<Tutor>> todosTutores() {
        return ResponseEntity.ok(adminService.listarTodosTutores());
    }

    // PATCH /api/admin/tutores/{id}/estado?activo=false
    @PatchMapping("/tutores/{id}/estado")
    public ResponseEntity<?> cambiarEstadoTutor(@PathVariable Long id,
                                                @RequestParam Boolean activo) {
        try {
            return ResponseEntity.ok(adminService.cambiarEstadoTutor(id, activo));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/admin/usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> todosUsuarios() {
        return ResponseEntity.ok(adminService.listarTodosUsuarios());
    }

    // GET /api/admin/estadisticas
    @GetMapping("/estadisticas")
    public ResponseEntity<?> estadisticas() {
        return ResponseEntity.ok(new java.util.HashMap<>() {{
            put("sesiones_pendientes",
                    adminService.contarSesionesPorEstado(Sesion.Estado.PENDIENTE));
            put("sesiones_confirmadas",
                    adminService.contarSesionesPorEstado(Sesion.Estado.CONFIRMADA));
            put("sesiones_completadas",
                    adminService.contarSesionesPorEstado(Sesion.Estado.COMPLETADA));
            put("sesiones_canceladas",
                    adminService.contarSesionesPorEstado(Sesion.Estado.CANCELADA));
            put("total_tutores",
                    adminService.listarTodosTutores().size());
            put("total_usuarios",
                    adminService.listarTodosUsuarios().size());
        }});
    }
}