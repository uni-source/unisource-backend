package com.UniSource.project_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "proposal")
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String status;

    private LocalDate submissionDate;

    private int studentId;

    private int projectId;

    private String publicId;

    private String publicUrl;
}
