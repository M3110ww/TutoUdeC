package com.example.tutoudec.dto.sesion;

import com.example.tutoudec.model.Sesion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SesionRequest {

    @NotNull(message = "Tutor is required")
    private Long tutorId;

    @NotNull(message = "Scheduled date is required")
    private LocalDateTime scheduledAt;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer durationMinutes;

    @NotNull(message = "Modality is required")
    private Sesion.Modality modality;

    private String meetingLink;
}