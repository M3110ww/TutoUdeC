package com.example.tutoudec.dto.student;

import com.example.tutoudec.model.Estudiante;
import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private String studentName;
    private String email;
    private String academicLevel;
    private String interests;

    public StudentResponse(Estudiante student) {
        this.id = student.getId();
        this.studentName = student.getUser().getName();
        this.email = student.getUser().getEmail();
        this.academicLevel = student.getAcademicLevel();
        this.interests = student.getInterests();
    }
}