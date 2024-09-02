package com.UniSource.project_service.controller;

import com.UniSource.project_service.dto.*;
import com.UniSource.project_service.entity.StudentHasProject;
import com.UniSource.project_service.exception.CustomException;
import com.UniSource.project_service.service.StudentHasProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-has-project")
@RequiredArgsConstructor
public class StudentHasProjectController {
    private final StudentHasProjectService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<StudentHasProject>> createStudentHasProject(@RequestBody CreateStudentHasProjectDTO dto) {
        try {
            StudentHasProject newStudentHasProject = service.createStudentHasProject(dto);
            ResponseDTO<StudentHasProject> response = new ResponseDTO<>(true, newStudentHasProject, "StudentHasProject created successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<StudentHasProject> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<StudentHasProject> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update-recommendation")
    public ResponseEntity<ResponseDTO<StudentHasProject>> updateRecommendation(@RequestBody UpdateRecommendationDTO dto) {
        try {
            StudentHasProject updatedStudentHasProject = service.updateRecommendation(dto);
            ResponseDTO<StudentHasProject> response = new ResponseDTO<>(true, updatedStudentHasProject, "Recommendation updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<StudentHasProject> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<StudentHasProject> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<ResponseDTO<StudentHasProject>> updateStatus(@RequestBody UpdateStatusDTO dto) {
        try {
            StudentHasProject updatedStudentHasProject = service.updateStatus(dto);
            ResponseDTO<StudentHasProject> response = new ResponseDTO<>(true, updatedStudentHasProject, "Status updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<StudentHasProject> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<StudentHasProject> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping()
    public ResponseEntity<ResponseDTO<List<StudentHasProjectResponseDTO>>> getAllStudentHasProjects() {
        try {
            List<StudentHasProjectResponseDTO> studentHasProjects = service.getAllStudentHasProjects();
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(true, studentHasProjects, "All StudentHasProjects retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<StudentHasProjectResponseDTO>> getStudentHasProjectById(@PathVariable int id) {
        try {
            StudentHasProjectResponseDTO studentHasProject = service.getStudentHasProjectById(id);
            ResponseDTO<StudentHasProjectResponseDTO> response = new ResponseDTO<>(true, studentHasProject, "StudentHasProject retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<StudentHasProjectResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<StudentHasProjectResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ResponseDTO<List<StudentHasProjectResponseDTO>>> getProjectsByStudentId(@PathVariable int studentId) {
        try {
            List<StudentHasProjectResponseDTO> studentHasProjects = service.getProjectsByStudentId(studentId);
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(true, studentHasProjects, "Projects retrieved successfully for student");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResponseDTO<StudentHasProjectResponseDTO>> getProjectByProjectId(@PathVariable int projectId) {
        try {
            StudentHasProjectResponseDTO studentHasProject = service.getProjectByProjectId(projectId);
            ResponseDTO<StudentHasProjectResponseDTO> response = new ResponseDTO<>(true, studentHasProject, "Project retrieved successfully by projectId");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<StudentHasProjectResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<StudentHasProjectResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ResponseDTO<List<StudentHasProjectResponseDTO>>> getProjectsByOrganizationId(@PathVariable int organizationId) {
        try {
            List<StudentHasProjectResponseDTO> studentHasProjects = service.getProjectsByOrganizationId(organizationId);
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(true, studentHasProjects, "Projects retrieved successfully for organization");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<ResponseDTO<List<StudentHasProjectResponseDTO>>> getProjectsByMentorId(@PathVariable int mentorId) {
        try {
            List<StudentHasProjectResponseDTO> studentHasProjects = service.getProjectsByMentorId(mentorId);
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(true, studentHasProjects, "Projects retrieved successfully for mentor");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<List<StudentHasProjectResponseDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}