package com.UniSource.project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalDetailsDTO {
    private int id;

    private String status;
    private LocalDate submissionDate;
    private int studentId;
    private String studentName;
    private int projectId;
    private String projectName;
    private String publicId;

    private String publicUrl;
}
