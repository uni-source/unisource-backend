package com.UniSource.project_service.controller;

import com.UniSource.project_service.dto.CreateProposalDTO;
import com.UniSource.project_service.dto.ResponseDTO;
import com.UniSource.project_service.dto.UpdateProposalStatusDTO;
import com.UniSource.project_service.entity.Proposal;
import com.UniSource.project_service.exception.CustomException;
import com.UniSource.project_service.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proposals")
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<Proposal>> createProposal(@RequestBody CreateProposalDTO dto) {
        try {
            Proposal newProposal = service.createProposal(dto);
            ResponseDTO<Proposal> response = new ResponseDTO<>(true, newProposal, "Proposal created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<Proposal> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<ResponseDTO<Proposal>> updateStatus(UpdateProposalStatusDTO dto) {
        try {
            Proposal updatedProposal = service.updateStatus(dto);
            ResponseDTO<Proposal> response = new ResponseDTO<>(true, updatedProposal, "Status updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Proposal> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Proposal> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<Proposal>>> getAllProposals() {
        try {
            List<Proposal> proposals = service.getAllProposals();
            ResponseDTO<List<Proposal>> response = new ResponseDTO<>(true, proposals, "All proposals retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<Proposal>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Proposal>> getProposalById(@PathVariable int id) {
        try {
            Proposal proposal = service.getProposalById(id);
            ResponseDTO<Proposal> response = new ResponseDTO<>(true, proposal, "Proposal retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Proposal> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Proposal> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ResponseDTO<List<Proposal>>> getProposalsByStudentId(@PathVariable int studentId) {
        try {
            List<Proposal> proposals = service.getProposalsByStudentId(studentId);
            ResponseDTO<List<Proposal>> response = new ResponseDTO<>(true, proposals, "Proposals for student retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<Proposal>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResponseDTO<List<Proposal>>> getProposalsByProjectId(@PathVariable int projectId) {
        try {
            List<Proposal> proposals = service.getProposalsByProjectId(projectId);
            ResponseDTO<List<Proposal>> response = new ResponseDTO<>(true, proposals, "Proposals for project retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<Proposal>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

