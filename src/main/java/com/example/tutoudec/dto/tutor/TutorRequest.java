package com.example.tutoudec.dto.tutor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TutorRequest {
    @NotBlank(message = "Speciality is required")
    private String specialty;

    private String description;
    @NotNull(message = "HourlyRate is required")
    @Positive(message = "Hourly rate must be greater than 0")
    private Double hourlyRate;
}
