package com.UniSource.project_service.client;

import com.UniSource.project_service.dto.OrganizationDetailsDTO;
import com.UniSource.project_service.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "organization-service",
        url = "http://localhost:8080"
)
public interface OrganizationClient {
    @GetMapping("/api/v1/organization/{id}")
    public ResponseEntity<ResponseDTO<OrganizationDetailsDTO>> getUserById(@PathVariable int id);
}
