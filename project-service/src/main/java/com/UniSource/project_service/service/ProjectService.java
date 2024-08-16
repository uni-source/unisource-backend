package com.UniSource.project_service.service;

import com.UniSource.project_service.client.MentorClient;
import com.UniSource.project_service.client.OrganizationClient;
import com.UniSource.project_service.dto.*;
import com.UniSource.project_service.entity.Project;
import com.UniSource.project_service.exception.CustomException;
import com.UniSource.project_service.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final OrganizationClient organizationClient;
    private final MentorClient mentorClient;
    public Project saveProject(CreateProjectDTO createProjectDTO) {
        ResponseEntity<ResponseDTO<OrganizationDetailsDTO>> orgResponse = organizationClient.getUserById(createProjectDTO.getOrganizationID());
        if (!orgResponse.getStatusCode().is2xxSuccessful() || orgResponse.getBody() == null || !orgResponse.getBody().getSuccess()) {
            throw new CustomException("Organization not found");
        }
        ResponseEntity<ResponseDTO<MentorDetails>> mentorResponse = mentorClient.getMentorById(createProjectDTO.getMentorID());
        if (!mentorResponse.getStatusCode().is2xxSuccessful() || mentorResponse.getBody() == null || !orgResponse.getBody().getSuccess()) {
            throw new CustomException("Mentor not found");
        }
        Project project =Project.build(
                0,
                createProjectDTO.getName(),
                createProjectDTO.getDescription(),
                createProjectDTO.getTitle(),
                createProjectDTO.getCategory(),
                createProjectDTO.getTechnologies(),
                createProjectDTO.getResource(),
                createProjectDTO.getDueDate(),
                "PENDING",
                createProjectDTO.getOrganizationID(),
                createProjectDTO.getMentorID()
        );
        return repository.save(project);
    }

    public Project updateProject( UpdateProjectRequestDTO request) {
        ResponseEntity<ResponseDTO<OrganizationDetailsDTO>> orgResponse = organizationClient.getUserById(request.getOrganizationID());
        if (!orgResponse.getStatusCode().is2xxSuccessful() || orgResponse.getBody() == null || !orgResponse.getBody().getSuccess()) {
            throw new CustomException("Organization not found");
        }
        ResponseEntity<ResponseDTO<MentorDetails>> mentorResponse = mentorClient.getMentorById(request.getMentorID());
        if (!mentorResponse.getStatusCode().is2xxSuccessful() || mentorResponse.getBody() == null || !orgResponse.getBody().getSuccess()) {
            throw new CustomException("Mentor not found");
        }
        Project project = repository.findById(request.getId())
                .orElseThrow(() -> new CustomException("Project not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setTitle(request.getTitle());
        project.setCategory(request.getCategory());
        project.setTechnologies(request.getTechnologies());
        project.setResource(request.getResource());
        project.setDueDate(request.getDueDate());
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




    public Project updateProjectStatus(UpdateProjectStatusRequestDTO request) {
        Project project = repository.findById(request.getId())
                .orElseThrow(() -> new CustomException("Project not found"));

        project.setStatus(request.getStatus());

        return repository.save(project);
    }

    public List<Project> projectSearchByMentorId(int mentorId) {
        MentorDetails mentorDetails = mentorClient.getMentorById( mentorId).getBody().getData();
        if (mentorDetails == null) {
            throw new CustomException("Mentor not found");
        }
        return repository.findAll().stream()
                .filter(project -> project.getMentorID() == mentorId)
                .collect(Collectors.toList());
    }

    public List<Project> projectSearchByOrganizationId(int organizationId) {
        OrganizationDetailsDTO organizationDetails = organizationClient.getUserById(organizationId).getBody().getData();
        if (organizationDetails == null) {
            throw new CustomException("Organization not found");
        }
        return repository.findAll().stream()
                .filter(project -> project.getOrganizationID() == organizationId)
                .collect(Collectors.toList());
    }

}
