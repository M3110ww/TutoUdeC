package com.example.tutoudec.service;

import com.example.tutoudec.model.*;
import com.example.tutoudec.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SesionService {

    private final SesionRepository sesionRepository;
    private final TutorRepository tutorRepository;
    private final EstudianteRepository estudianteRepository;
    private final DisponibilidadRepository disponibilidadRepository;

    public SesionService(SesionRepository sesionRepository,
                         TutorRepository tutorRepository,
                         EstudianteRepository estudianteRepository,
                         DisponibilidadRepository disponibilidadRepository) {
        this.sesionRepository = sesionRepository;
        this.tutorRepository = tutorRepository;
        this.estudianteRepository = estudianteRepository;
        this.disponibilidadRepository = disponibilidadRepository;
    }

    public List<Sesion> listarTodas() {
        return sesionRepository.findAll();
    }

    public List<Sesion> listarPorTutor(Long tutorId) {
        return sesionRepository.findByTutorId(tutorId);
    }

    public List<Sesion> listarPorEstudiante(Long estudianteId) {
        return sesionRepository.findByEstudianteId(estudianteId);
    }

    public List<Sesion> listarPorEstado(Sesion.Estado estado) {
        return sesionRepository.findByEstado(estado);
    }

    public Optional<Sesion> buscarPorId(Long id) {
        return sesionRepository.findById(id);
    }

    public Sesion reservar(Long tutorId, Long estudianteId, Sesion sesion) {
        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new RuntimeException("Tutor no encontrado"));

        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        if (!tutor.getActivo()) {
            throw new RuntimeException("El tutor no está disponible");
        }

        // Validar disponibilidad del tutor
        int diaSemana = sesion.getFechaHora().getDayOfWeek().getValue();
        List<Disponibilidad> disponibilidades = disponibilidadRepository
                .findByTutorIdAndDiaSemana(tutorId, diaSemana);

        boolean disponible = disponibilidades.stream().anyMatch(d ->
                !sesion.getFechaHora().toLocalTime().isBefore(d.getHoraInicio()) &&
                        !sesion.getFechaHora().toLocalTime().isAfter(d.getHoraFin())
        );

        if (!disponible) {
            throw new RuntimeException("El tutor no tiene disponibilidad en ese horario");
        }

        // Calcular costo automáticamente
        double costoTotal = (sesion.getDuracionMin() / 60.0) * tutor.getTarifaHora();

        sesion.setTutor(tutor);
        sesion.setEstudiante(estudiante);
        sesion.setCostoTotal(costoTotal);
        sesion.setEstado(Sesion.Estado.PENDIENTE);

        return sesionRepository.save(sesion);
    }

    public Sesion cambiarEstado(Long id, Sesion.Estado estado) {
        Sesion sesion = sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));
        sesion.setEstado(estado);
        return sesionRepository.save(sesion);
    }

    public Sesion cancelar(Long id) {
        return cambiarEstado(id, Sesion.Estado.CANCELADA);
    }

    public Sesion confirmar(Long id) {
        return cambiarEstado(id, Sesion.Estado.CONFIRMADA);
    }

    public Sesion completar(Long id) {
        return cambiarEstado(id, Sesion.Estado.COMPLETADA);
    }
}