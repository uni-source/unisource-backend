package com.UniSource.project_service.service;

import com.UniSource.project_service.dto.UpdateProjectRequestDTO;
import com.UniSource.project_service.entity.Project;
import com.UniSource.project_service.exception.CustomException;
import com.UniSource.project_service.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;

    public Project saveProject(Project project) {
        return repository.save(project);
    }

    public Project updateProject( UpdateProjectRequestDTO request) {
        Project project = repository.findById(request.getId())
                .orElseThrow(() -> new CustomException("Project not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setTitle(request.getTitle());
        project.setCategory(request.getCategory());
        project.setTechnologies(request.getTechnologies());
        project.setResource(request.getResource());
        project.setDueDate(request.getDueDate());
        project.setStatus(request.getStatus());
        project.setOrganizationID(request.getOrganizationID());
        project.setMentorID(request.getMentorID());

        return repository.save(project);
    }

    public Project getProjectById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException("Project not found"));
    }
    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    public void deleteProject(int id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new CustomException("Project not found"));
        repository.delete(project);
    }









}
