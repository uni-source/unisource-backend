package com.UniSource.project_service.service;

import com.UniSource.project_service.client.StudentClient;
import com.UniSource.project_service.dto.*;
import com.UniSource.project_service.entity.Project;
import com.UniSource.project_service.entity.StudentHasProject;
import com.UniSource.project_service.exception.CustomException;
import com.UniSource.project_service.repository.ProjectRepository;
import com.UniSource.project_service.repository.StudentHasProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentHasProjectService {
    private final StudentHasProjectRepository repository;
    private final StudentClient studentClient;
    private final ProjectRepository projectRepository;
    public StudentHasProject createStudentHasProject(CreateStudentHasProjectDTO dto) {
        ResponseEntity<ResponseDTO<StudentDetailsDTO>> studentResponse = studentClient.getUserById(dto.getStudentId());
        if (!studentResponse.getStatusCode().is2xxSuccessful() || studentResponse.getBody() == null || !studentResponse.getBody().getSuccess()) {
            throw new CustomException("Organization not found");
        }
        Project project =projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new CustomException("Project not found"));
        StudentHasProject studentHasProject = StudentHasProject.build(
                0,
                studentResponse.getBody().getData().getIdentityId(),
                project.getId(),
                "PROCESS",
                ""
        );
        return repository.save(studentHasProject);
    }
    public StudentHasProject updateRecommendation(UpdateRecommendationDTO dto) {
        StudentHasProject studentHasProject = repository.findById(dto.getId())
                .orElseThrow(() -> new CustomException("StudentHasProject not found"));
        studentHasProject.setRecommendation(dto.getRecommendation());
        return repository.save(studentHasProject);
    }

    public StudentHasProject updateStatus(UpdateStatusDTO dto) {
        StudentHasProject studentHasProject = repository.findById(dto.getId())
                .orElseThrow(() -> new CustomException("StudentHasProject not found"));
        studentHasProject.setStatus(dto.getStatus());
        return repository.save(studentHasProject);
    }
    public List<StudentHasProjectResponseDTO> getAllStudentHasProjects() {
        List<StudentHasProject> studentHasProjects = repository.findAll();
        return studentHasProjects.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    public StudentHasProjectResponseDTO getStudentHasProjectById(int id) {
        StudentHasProject studentHasProject = repository.findById(id)
                .orElseThrow(() -> new CustomException("StudentHasProject not found"));
        return mapToResponseDTO(studentHasProject);
    }

    public List<StudentHasProjectResponseDTO> getProjectsByStudentId(int studentId) {
        List<StudentHasProject> studentHasProjects = repository.findByStudentId(studentId);
        if (studentHasProjects.isEmpty()) {
            throw new CustomException("No projects found for this student");
        }
        return studentHasProjects.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    public StudentHasProjectResponseDTO getProjectByProjectId(int projectId) {
        StudentHasProject studentHasProject = repository.findByProjectId(projectId)
                .orElseThrow(() -> new CustomException("Project not found"));
        return mapToResponseDTO(studentHasProject);
    }
    private StudentHasProjectResponseDTO mapToResponseDTO(StudentHasProject studentHasProject) {
        ResponseEntity<ResponseDTO<StudentDetailsDTO>> studentResponse = studentClient.getUserById(studentHasProject.getStudentId());
        if (!studentResponse.getStatusCode().is2xxSuccessful() || studentResponse.getBody() == null || !studentResponse.getBody().getSuccess()) {
            throw new CustomException("Student not found");
        }
        Project project = projectRepository.findById(studentHasProject.getProjectId())
                .orElseThrow(() -> new CustomException("Project not found"));

        StudentDetailsDTO studentDetails = studentResponse.getBody().getData();

        StudentHasProjectResponseDTO responseDTO = new StudentHasProjectResponseDTO();
        responseDTO.setId(studentHasProject.getId());
        responseDTO.setStudentId(studentHasProject.getStudentId());
        responseDTO.setStudentName(studentDetails.getName());
        responseDTO.setProjectId(studentHasProject.getProjectId());
        responseDTO.setProjectName(project.getName());
        responseDTO.setStatus(studentHasProject.getStatus());
        responseDTO.setRecommendation(studentHasProject.getRecommendation());

        return responseDTO;
    }
}
