package com.UniSource.project_service.service;

import com.UniSource.project_service.dto.CreateProposalDTO;
import com.UniSource.project_service.dto.UpdateProposalStatusDTO;
import com.UniSource.project_service.entity.Proposal;
import com.UniSource.project_service.exception.CustomException;
import com.UniSource.project_service.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProposalService {
    private final ProposalRepository repository;

    public Proposal createProposal(CreateProposalDTO dto) {
        Proposal proposal = Proposal.build(
                0,
                dto.getStatus(),
                dto.getSubmissionDate(),
                dto.getStudentId(),
                dto.getProjectId(),
                dto.getPublicId(),
                dto.getPublicUrl()
        );
        return repository.save(proposal);
    }

    public Proposal updateStatus(UpdateProposalStatusDTO dto) {
        Proposal proposal = repository.findById(dto.getId())
                .orElseThrow(() -> new CustomException("Proposal not found"));
        proposal.setStatus(dto.getStatus());
        return repository.save(proposal);
    }

    public Proposal getProposalById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomException("Proposal not found"));
    }

    public List<Proposal> getAllProposals() {
        return repository.findAll();
    }

    public List<Proposal> getProposalsByStudentId(int studentId) {
        return repository.findByStudentId(studentId);
    }

    public List<Proposal> getProposalsByProjectId(int projectId) {
        return repository.findByProjectId(projectId);
    }
}

