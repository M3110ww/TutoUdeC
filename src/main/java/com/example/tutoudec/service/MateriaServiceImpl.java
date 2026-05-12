package com.example.tutoudec.service;

import com.example.tutoudec.exception.ResourceNotFoundException;
import com.example.tutoudec.model.Materia;
import com.example.tutoudec.repository.MateriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Materia create(Materia subject) {
        return materiaRepository.save(subject);
    }

    @Override
    public Materia update(Long id, Materia subject) {
        Materia existing = materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
        existing.setName(subject.getName());
        existing.setCategory(subject.getCategory());
        return materiaRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Materia findById(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> getAll() {
        return materiaRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found with id: " + id);
        }
        materiaRepository.deleteById(id);
    }
}
