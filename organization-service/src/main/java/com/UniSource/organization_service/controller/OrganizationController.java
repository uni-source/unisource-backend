package com.UniSource.organization_service.controller;

import com.UniSource.organization_service.dto.*;
import com.UniSource.organization_service.entity.Organization;
import com.UniSource.organization_service.exception.CustomException;
import com.UniSource.organization_service.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;
    @PostMapping
    public ResponseEntity<ResponseDTO<Organization>> createOrganization(
            @RequestBody createOrganizationDTO request) {
        try{
            Organization newOrganization=service.saveOrganization(Organization.build(0, false, "",  request.getIdentityId(),"",""));
            ResponseDTO<Organization> response = new ResponseDTO<>(true, newOrganization, "Organization registered is successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Organization> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Organization> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<OrganizationDetailsDTO>> getUserById(@PathVariable int id) {
        try {
            OrganizationDetailsDTO organization = service.getOrganizationById(id);
            ResponseDTO<OrganizationDetailsDTO > response = new ResponseDTO<>(true, organization, "User retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<OrganizationDetailsDTO > response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<OrganizationDetailsDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping("/description")
    public ResponseEntity<ResponseDTO<Organization>> updateUser(@RequestBody UpdateOrganizationRequestDTO request) {
        try {
            Organization user = service.UpdateDescription(request);
            ResponseDTO<Organization> response = new ResponseDTO<>(true, user, "Organization updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Organization> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Organization> response = new ResponseDTO<>(false, null,  e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/upload-image")
    public ResponseEntity<ResponseDTO<OrganizationDetailsDTO>> updateProfileImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("public_id") String publicId,
            @RequestParam("identityId") int identityId) {
        try {
            imageRequestDTO request = new imageRequestDTO();
            request.setFile(file);
            request.setPublic_id(publicId);
            request.setIdentityId(identityId);

            OrganizationDetailsDTO user = service.UpdateProfileImage(request);
            ResponseDTO<OrganizationDetailsDTO> response = new ResponseDTO<>(true, user, "Organization Profile Image updated Successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<OrganizationDetailsDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<OrganizationDetailsDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    // place organization verify method here
}
