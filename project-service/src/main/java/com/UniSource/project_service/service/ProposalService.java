package com.UniSource.project_service.service;

import com.UniSource.project_service.client.StudentClient;
import com.UniSource.project_service.dto.*;
import com.UniSource.project_service.entity.Project;
import com.UniSource.project_service.entity.Proposal;
import com.UniSource.project_service.exception.CustomException;
import com.UniSource.project_service.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProposalService {
    private final ProposalRepository repository;
    private final ProjectService projectService;
    private final StudentClient studentClient;
    public Proposal createProposal(CreateProposalDTO dto, String publicId, String publicUrl) {
        Proposal proposal = Proposal.build(
                0,
                dto.getStatus(),
                dto.getSubmissionDate(),
                dto.getStudentId(),
                dto.getProjectId(),
                publicId,
                publicUrl,
                dto.getOrganizationId(),
                dto.getMentorId()
        );
        return repository.save(proposal);
    }

    public Proposal updateStatus(UpdateProposalStatusDTO dto) {
        System.out.println("Updating proposal with id: " + dto.getId());
        Proposal proposal = repository.findById(dto.getId())
                .orElseThrow(() -> new CustomException("Proposal not found"));
        System.out.println("Found proposal: " + proposal);
        proposal.setStatus(dto.getStatus());
        return repository.save(proposal);
    }


    public ProposalDetailsDTO getProposalById(int id) {
        Proposal proposal = repository.findById(id)
                .orElseThrow(() -> new CustomException("Proposal not found"));
        return mapToProposalDetailsDTO(proposal);
    }

    public List<ProposalDetailsDTO> getAllProposals() {
        return repository.findAll().stream()
                .map(this::mapToProposalDetailsDTO)
                .collect(Collectors.toList());
    }

    public List<ProposalDetailsDTO> getProposalsByStudentId(int studentId) {
        return repository.findByStudentId(studentId).stream()
                .map(this::mapToProposalDetailsDTO)
                .collect(Collectors.toList());
    }

    public List<ProposalDetailsDTO> getProposalsByProjectId(int projectId) {
        return repository.findByProjectId(projectId).stream()
                .map(this::mapToProposalDetailsDTO)
                .collect(Collectors.toList());
    }
    public List<ProposalDetailsDTO> getProposalsByOrganizationId(int organizationId) {
        return repository.findByOrganizationId(organizationId).stream()
                .map(this::mapToProposalDetailsDTO)
                .collect(Collectors.toList());
    }
    public List<ProposalDetailsDTO> getProposalsByMentorId(int mentorId) {
        return repository.findByMentorId(mentorId).stream()
                .map(this::mapToProposalDetailsDTO)
                .collect(Collectors.toList());
    }
    private ProposalDetailsDTO mapToProposalDetailsDTO(Proposal proposal) {
        Project project = projectService.getProjectById(proposal.getProjectId());
        ResponseEntity<ResponseDTO<StudentDetailsDTO>> studentResponse = studentClient.getUserById(proposal.getStudentId());
        if (!studentResponse.getStatusCode().is2xxSuccessful() || studentResponse.getBody() == null || !studentResponse.getBody().getSuccess()) {
            throw new CustomException("Student not found");
        }

        StudentDetailsDTO studentDetails = studentResponse.getBody().getData();

        ProposalDetailsDTO dto = new ProposalDetailsDTO();
        dto.setId(proposal.getId());
        dto.setStatus(proposal.getStatus());
        dto.setSubmissionDate(proposal.getSubmissionDate());
        dto.setStudentId(proposal.getStudentId());
        dto.setStudentName(studentDetails.getName());
        dto.setProjectId(proposal.getProjectId());
        dto.setProjectName(project.getName());
        dto.setPublicId(proposal.getPublicId());
        dto.setPublicUrl(proposal.getPublicUrl());

        return dto;
    }
}