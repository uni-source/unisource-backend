package com.UniSource.identity_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDTO {
    private int id;
    @NotBlank(message = "Name is required")
    private String name;
}
