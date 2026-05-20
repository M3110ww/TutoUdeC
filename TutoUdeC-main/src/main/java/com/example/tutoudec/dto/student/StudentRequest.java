package com.example.tutoudec.dto.student;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentRequest {

    @NotBlank(message = "Academic Level is required")
    private String academicLevel;

    private String interest; // Asegúrate que NO tenga la 's' al final si el backend lo procesa así

    // Getters y Setters
    public String getAcademicLevel() { return academicLevel; }
    public void setAcademicLevel(String academicLevel) { this.academicLevel = academicLevel; }
    public String getInterest() { return interest; }
    public void setInterest(String interest) { this.interest = interest; }
}