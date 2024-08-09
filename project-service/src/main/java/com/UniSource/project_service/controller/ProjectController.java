package com.UniSource.project_service.controller;

import com.UniSource.project_service.dto.*;
import com.UniSource.project_service.entity.Project;
import com.UniSource.project_service.exception.CustomException;
import com.UniSource.project_service.service.ProjectService;
import com.UniSource.project_service.dto.CreateProjectDTO;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;
    @PostMapping
    public ResponseEntity<ResponseDTO<Project>> createProject(@RequestBody CreateProjectDTO createProjectDTO) {
        try {
            Project newProject = service.saveProject(createProjectDTO);
            ResponseDTO<Project> response = new ResponseDTO<>(true, newProject, "Project added successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Project> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Project> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping()
    public ResponseEntity<ResponseDTO<Project>> updateProject( @RequestBody UpdateProjectRequestDTO updateProjectDTO) {
        try {
            Project updatedProject = service.updateProject( updateProjectDTO);
            ResponseDTO<Project> response = new ResponseDTO<>(true, updatedProject, "Project updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Project> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Project> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Project>> getProjectById(@PathVariable int id) {
        try {
            Project project = service.getProjectById(id);
            ResponseDTO<Project> response = new ResponseDTO<>(true, project, "Project retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Project> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Project> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping
    public ResponseEntity<ResponseDTO<List<Project>>> getAllProjects() {
        try {
            List<Project> projects = service.getAllProjects();
            ResponseDTO<List<Project>> response = new ResponseDTO<>(true, projects, "Projects retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<Project>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteProject(@PathVariable int id) {
        try {
            service.deleteProject(id);
            ResponseDTO<Void> response = new ResponseDTO<>(true, null, "Project deleted successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }






    public ResponseEntity<ResponseDTO<Project>> updateProjectStatus(@RequestBody UpdateProjectStatusRequestDTO updateProjectStatusDTO) {
        try {
            Project updatedProjectStatus = service.updateProjectStatus(updateProjectStatusDTO);
            ResponseDTO<Project> response = new ResponseDTO<>(true, updatedProjectStatus, "Project Status updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Project> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Project> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<ResponseDTO<List<Project>>> projectSearchByMentorId(@PathVariable int mentorId) {
        try {
            List<Project> projects = service.projectSearchByMentorId(mentorId);
            ResponseDTO<List<Project>> response = new ResponseDTO<>(true, projects, "Projects retrieved successfully by Mentor ID");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<List<Project>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<List<Project>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<ResponseDTO<List<Project>>> projectSearchByOrganizationId(@PathVariable int organizationId) {
        try {
            List<Project> projects = service.projectSearchByOrganizationId(organizationId);
            ResponseDTO<List<Project>> response = new ResponseDTO<>(true, projects, "Projects retrieved successfully by Organization ID");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<List<Project>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<List<Project>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }








}
