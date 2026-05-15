package com.example.tutoudec.service;

import com.example.tutoudec.dto.tutor.TutorRequest;
import com.example.tutoudec.dto.tutor.TutorResponse;
import com.example.tutoudec.exception.DuplicateResourceException;
import com.example.tutoudec.exception.ResourceNotFoundException;
import com.example.tutoudec.model.Tutor;
import com.example.tutoudec.model.Usuario;
import com.example.tutoudec.repository.TutorRepository;
import com.example.tutoudec.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;
    private final UsuarioRepository usuarioRepository;

    public TutorServiceImpl(TutorRepository tutorRepository,
                            UsuarioRepository usuarioRepository) {
        this.tutorRepository = tutorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public TutorResponse registerTutor(Long userId, TutorRequest request) {
        Usuario user = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // A user can only have one tutor profile
        if (tutorRepository.findByUserId(userId).isPresent()) {
            throw new DuplicateResourceException("Tutor profile already exists for user: " + userId);
        }

        Tutor tutor = new Tutor();
        tutor.setUser(user);
        tutor.setSpecialty(request.getSpecialty());
        tutor.setDescription(request.getDescription());
        tutor.setHourlyRate(request.getHourlyRate());
        tutor.setActive(true);
        tutor.setAverageRating(0.0);

        return new TutorResponse(tutorRepository.save(tutor));
    }

    @Override
    public TutorResponse update(Long id, TutorRequest request) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with id: " + id));

        tutor.setSpecialty(request.getSpecialty());
        tutor.setDescription(request.getDescription());
        tutor.setHourlyRate(request.getHourlyRate());

        return new TutorResponse(tutorRepository.save(tutor));
    }

    @Override
    @Transactional(readOnly = true)
    public TutorResponse findById(Long id) {
        return tutorRepository.findById(id)
                .map(TutorResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorResponse> getAll() {
        return tutorRepository.findAll()
                .stream()
                .map(TutorResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorResponse> getActive() {
        return tutorRepository.findByActiveTrue()
                .stream()
                .map(TutorResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorResponse> searchBySpecialty(String specialty) {
        return tutorRepository.findBySpecialtyContainingIgnoreCase(specialty)
                .stream()
                .map(TutorResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public TutorResponse changeStatus(Long id, Boolean active) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with id: " + id));
        tutor.setActive(active);
        return new TutorResponse(tutorRepository.save(tutor));
    }
}
