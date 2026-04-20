package com.example.tutoudec.service;

import com.example.tutoudec.model.Resena;
import com.example.tutoudec.model.Sesion;
import com.example.tutoudec.model.Estudiante;
import com.example.tutoudec.repository.ResenaRepository;
import com.example.tutoudec.repository.SesionRepository;
import com.example.tutoudec.repository.EstudianteRepository;
import com.example.tutoudec.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final SesionRepository sesionRepository;
    private final EstudianteRepository estudianteRepository;
    private final TutorRepository tutorRepository;

    public ResenaService(ResenaRepository resenaRepository,
                         SesionRepository sesionRepository,
                         EstudianteRepository estudianteRepository,
                         TutorRepository tutorRepository) {
        this.resenaRepository = resenaRepository;
        this.sesionRepository = sesionRepository;
        this.estudianteRepository = estudianteRepository;
        this.tutorRepository = tutorRepository;
    }

    public List<Resena> listarPorEstudiante(Long estudianteId) {
        return resenaRepository.findByEstudianteId(estudianteId);
    }

    public List<Resena> listarPorTutor(Long tutorId) {
        return resenaRepository.findBySesionTutorId(tutorId);
    }

    public Optional<Resena> buscarPorSesion(Long sesionId) {
        return resenaRepository.findBySesionId(sesionId);
    }

    public Resena crear(Long sesionId, Long estudianteId, Resena resena) {
        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        if (sesion.getEstado() != Sesion.Estado.COMPLETADA) {
            throw new RuntimeException("Solo se puede reseñar una sesión completada");
        }

        if (resenaRepository.findBySesionId(sesionId).isPresent()) {
            throw new RuntimeException("Esta sesión ya tiene una reseña");
        }

        if (resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
            throw new RuntimeException("La calificación debe estar entre 1 y 5");
        }

        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        resena.setSesion(sesion);
        resena.setEstudiante(estudiante);
        Resena guardada = resenaRepository.save(resena);

        // Actualizar calificación promedio del tutor
        Long tutorId = sesion.getTutor().getId();
        List<Resena> resenas = resenaRepository.findBySesionTutorId(tutorId);
        double promedio = resenas.stream()
                .mapToInt(Resena::getCalificacion)
                .average()
                .orElse(0.0);

        tutorRepository.findById(tutorId).ifPresent(tutor -> {
            tutor.setCalificacionPromedio(promedio);
            tutorRepository.save(tutor);
        });

        return guardada;
    }
}