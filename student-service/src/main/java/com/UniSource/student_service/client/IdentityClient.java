package com.UniSource.student_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "identity-service",
        url = "http://localhost:8080/api/v1/auth"
)
public interface IdentityClient {
    @GetMapping("/{id}")
    public Optional<IdentityResponse<User>> getUserById(@PathVariable int id);
}
