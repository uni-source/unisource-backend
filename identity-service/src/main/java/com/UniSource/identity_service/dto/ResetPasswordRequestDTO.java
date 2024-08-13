package com.UniSource.identity_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestDTO {
    private int id;
    @NotBlank(message = "New Password is required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
    @NotBlank(message = "Old Password is required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String oldPassword;
}
