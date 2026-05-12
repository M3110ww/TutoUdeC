package com.example.tutoudec.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tutors")
@Data
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Usuario user;

    @Column(length = 100)
    private String specialty;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "hourly_rate", nullable = false)
    private Double hourlyRate;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "average_rating")
    private Double averageRating = 0.0;
}
