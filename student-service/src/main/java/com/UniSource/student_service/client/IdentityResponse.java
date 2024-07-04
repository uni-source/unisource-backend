package com.UniSource.student_service.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityResponse<T> {
    private Boolean success;
    private T data;
    private String message;
}
