package com.UniSource.identity_service.client;

import com.UniSource.identity_service.dto.ResponseDTO;
import com.UniSource.identity_service.dto.createOrganizationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "organization-service",
        url = "http://localhost:8080/api/v1/organization"
)
public interface OrganizationClient {
    @PostMapping
    public ResponseEntity<ResponseDTO<Organization>> createOrganization(
            @RequestBody createOrganizationDTO request,
            @RequestHeader("Authorization") String token);
}
