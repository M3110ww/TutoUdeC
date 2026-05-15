package com.example.tutoudec.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "availability")
@Data
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Tutor tutor;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Boolean recurring = true;

    @Column(nullable = false)
    private Boolean occupied = false;
}
