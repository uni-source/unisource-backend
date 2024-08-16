package com.UniSource.admin_service.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenExtractor {
    private final HttpServletRequest request;

    @Autowired
    public TokenExtractor(HttpServletRequest request) {
        this.request = request;
    }

    public String extractToken() {
        // Example: Extract token from Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null; // Handle if token extraction fails
    }
}
