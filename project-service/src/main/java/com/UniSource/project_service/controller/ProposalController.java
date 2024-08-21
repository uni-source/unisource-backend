package com.UniSource.project_service.controller;

import com.UniSource.project_service.dto.CreateProposalDTO;
import com.UniSource.project_service.dto.ProposalDetailsDTO;
import com.UniSource.project_service.dto.ResponseDTO;
import com.UniSource.project_service.dto.UpdateProposalStatusDTO;
import com.UniSource.project_service.entity.Proposal;
import com.UniSource.project_service.exception.CustomException;
import com.UniSource.project_service.service.CloudinaryService;
import com.UniSource.project_service.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/proposal")
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalService service;
    private final CloudinaryService cloudinaryService;
    @PostMapping
    public ResponseEntity<ResponseDTO<Proposal>> createProposal(
            @RequestParam("status") String status,
            @RequestParam("submissionDate") LocalDate submissionDate,
            @RequestParam("studentId") int studentId,
            @RequestParam("projectId") int projectId,
            @RequestPart("file") MultipartFile file,
            @RequestParam("organizationId") int organizationId,
            @RequestParam("mentorId") int mentorId) {

        try {
            Map<String, String> uploadResult = cloudinaryService.uploadImage(file.getBytes());
            String publicId = uploadResult.get("public_id");
            String publicUrl = uploadResult.get("secure_url");

            CreateProposalDTO dto = new CreateProposalDTO();
            dto.setStatus(status);
            dto.setSubmissionDate(submissionDate);
            dto.setStudentId(studentId);
            dto.setProjectId(projectId);
            dto.setFile(file);
            dto.setOrganizationId(organizationId);
            dto.setMentorId(mentorId);

            Proposal newProposal = service.createProposal(dto, publicId, publicUrl);
            ResponseDTO<Proposal> response = new ResponseDTO<>(true, newProposal, "Proposal created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<Proposal> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping("/update-status")
    public ResponseEntity<ResponseDTO<Proposal>> updateStatus(@RequestBody UpdateProposalStatusDTO dto) {
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
    public ResponseEntity<ResponseDTO<List<ProposalDetailsDTO>>> getAllProposals() {
        try {
            List<ProposalDetailsDTO> proposals = service.getAllProposals();
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(true, proposals, "All proposals retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProposalDetailsDTO>> getProposalById(@PathVariable int id) {
        try {
            ProposalDetailsDTO proposalDetailsDTO = service.getProposalById(id);
            ResponseDTO<ProposalDetailsDTO> response = new ResponseDTO<>(true, proposalDetailsDTO, "Proposal retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<ProposalDetailsDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<ProposalDetailsDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ResponseDTO<List<ProposalDetailsDTO>>> getProposalsByStudentId(@PathVariable int studentId) {
        try {
            List<ProposalDetailsDTO> proposals = service.getProposalsByStudentId(studentId);
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(true, proposals, "Proposals for student retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResponseDTO<List<ProposalDetailsDTO>>> getProposalsByProjectId(@PathVariable int projectId) {
        try {
            List<ProposalDetailsDTO> proposals = service.getProposalsByProjectId(projectId);
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(true, proposals, "Proposals for project retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ResponseDTO<List<ProposalDetailsDTO>>> getProposalsByOrganizationId(@PathVariable int organizationId) {
        try {
            List<ProposalDetailsDTO> proposals = service.getProposalsByOrganizationId(organizationId);
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(true, proposals, "Proposals for project retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<ResponseDTO<List<ProposalDetailsDTO>>> getProposalsByMentorId(@PathVariable int mentorId) {
        try {
            List<ProposalDetailsDTO> proposals = service.getProposalsByMentorId(mentorId);
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(true, proposals, "Proposals for project retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<ProposalDetailsDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}