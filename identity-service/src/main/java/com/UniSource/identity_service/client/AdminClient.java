package com.UniSource.identity_service.client;

import com.UniSource.identity_service.dto.CreateRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "admin-service",
        url = "http://localhost:8080/api/v1/admin"
)
public interface AdminClient {
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody CreateRequestDTO request,
                                             @RequestHeader("Authorization") String token);
}
