package com.example.tutoudec.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Usuario user;

    @Column(name = "academic_level", length = 50)
    private String academicLevel;

    @Column(columnDefinition = "TEXT")
    private String interests;
}

