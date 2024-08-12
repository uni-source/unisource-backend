package com.UniSource.project_service.client;

import com.UniSource.project_service.dto.ResponseDTO;
import com.UniSource.project_service.dto.StudentDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "student-service",
        url = "http://localhost:8080"
)
public interface StudentClient {
    @GetMapping("/api/v1/student/{id}")
    public ResponseEntity<ResponseDTO<StudentDetailsDTO>> getUserById(@PathVariable int id);
}
