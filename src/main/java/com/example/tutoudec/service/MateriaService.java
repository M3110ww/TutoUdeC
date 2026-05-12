package com.example.tutoudec.service;

import com.example.tutoudec.model.Materia;
import java.util.List;

public interface MateriaService {
    Materia create(Materia subject);
    Materia update(Long id, Materia subject);
    Materia findById(Long id);
    List<Materia> getAll();
    void delete(Long id);
}

