package com.UniSource.organization_service.client;

import com.UniSource.organization_service.dto.RegisterDTO;
import com.UniSource.organization_service.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
        name = "identity-service",
        url = "http://localhost:8080/api/v1/auth"
)
public interface IdentityClient {
    @GetMapping("/{id}")
    public Optional<IdentityResponse<User>> getUserById(@PathVariable int id);
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<User>> register(
            @RequestBody RegisterDTO request
    );
}
