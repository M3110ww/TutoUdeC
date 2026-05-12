package com.example.tutoudec.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tutor_subjects")
@Data
public class TutorMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Materia subject;
}
