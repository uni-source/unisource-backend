package com.UniSource.project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProposalDTO {
    private String status;
    private LocalDate submissionDate;
    private int studentId;
    private int projectId;
    private MultipartFile file;
}