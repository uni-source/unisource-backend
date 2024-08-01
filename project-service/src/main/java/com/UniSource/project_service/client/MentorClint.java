package com.UniSource.project_service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "organization-service",
        url = "http://localhost:8080/api/v1/student"
)
public interface MentorClint {
}
