package com.UniSource.project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentHasProjectResponseDTO {
    private int id;
    private int studentId;
    private String studentName;
    private int projectId;
    private String projectName;
    private String status;
    private String recommendation;
}
