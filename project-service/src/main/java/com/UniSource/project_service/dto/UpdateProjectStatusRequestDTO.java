package com.UniSource.project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateProjectStatusRequestDTO {
    private int id;
    private String status;
}
