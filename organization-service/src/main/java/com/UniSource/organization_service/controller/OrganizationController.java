package com.UniSource.organization_service.controller;

import com.UniSource.organization_service.dto.ResponseDTO;
import com.UniSource.organization_service.dto.createOrganizationDTO;
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
            Organization newStudent=service.saveOrganization(Organization.build(0, false, "",  request.getIdentityId(),"",""));
            ResponseDTO<Organization> response = new ResponseDTO<>(true, newStudent, "Organization registered is successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Organization> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Organization> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
