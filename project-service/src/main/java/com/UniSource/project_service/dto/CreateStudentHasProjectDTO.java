package com.UniSource.project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentHasProjectDTO {
    private int studentId;
    private int projectId;
}
