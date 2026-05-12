package com.example.tutoudec.dto.student;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentRequest {

    @NotBlank(message = "Academic Level is required")
    private String academicLevel;

    private String interest;
}
