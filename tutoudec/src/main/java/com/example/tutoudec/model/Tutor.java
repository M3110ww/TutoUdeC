package com.example.tutoudec.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tutores")
@Data
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(length = 100)
    private String especialidad;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tarifa_hora", nullable = false)
    private Double tarifaHora;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "calificacion_promedio")
    private Double calificacionPromedio = 0.0;
}