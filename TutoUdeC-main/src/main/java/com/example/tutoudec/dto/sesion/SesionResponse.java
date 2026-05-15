package com.example.tutoudec.dto.sesion;

import com.example.tutoudec.model.Sesion;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SesionResponse {
    private Long id;
    private String tutorName;
    private String studentName;
    private String subject;
    private LocalDateTime scheduledAt;
    private Integer durationMinutes;
    private Double totalCost;
    private Sesion.Status status;
    private Sesion.Modality modality;
    private String meetingLink;
    private LocalDateTime createdAt;

    public SesionResponse(Sesion s) {
        this.id = s.getId();
        this.tutorName = s.getTutor().getUser().getName();
        this.studentName = s.getStudent().getUser().getName();
        this.subject = s.getSubject() != null ? s.getSubject().getName() : null;
        this.scheduledAt = s.getScheduledAt();
        this.durationMinutes = s.getDurationMinutes();
        this.totalCost = s.getTotalCost();
        this.status = s.getStatus();
        this.modality = s.getModality();
        this.meetingLink = s.getMeetingLink();
        this.createdAt = s.getCreatedAt();
    }
}