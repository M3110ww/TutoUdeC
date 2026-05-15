package com.example.tutoudec.dto.tutor;

import com.example.tutoudec.model.Tutor;
import lombok.Data;

@Data
public class TutorResponse {
    private Long id;
    private String tutorName;
    private Boolean active;
    private String email;
    private String description;
    private double hourlyRate;
    private double averageRating;
    private String specialty;

    public TutorResponse(Tutor tutor){
        this.id = tutor.getId();
        this.tutorName = tutor.getUser().getName();
        this.active= tutor.getActive();
        this.email = tutor.getUser().getEmail();
        this.description = tutor.getDescription();
        this.hourlyRate = tutor.getHourlyRate();
        this.averageRating = tutor.getAverageRating();
        this.specialty = tutor.getSpecialty();
    }
}
