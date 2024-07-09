package com.UniSource.identity_service.client;

import com.UniSource.identity_service.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "student-service",
        url = "http://localhost:8080/api/v1/student"
)
public interface StudentClient {
    @PostMapping("/")
    public ResponseEntity<ResponseDTO<Student>> createStudent(@RequestBody int identityId, @RequestHeader("Authorization") String token);

}
