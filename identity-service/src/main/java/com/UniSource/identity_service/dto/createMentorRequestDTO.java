package com.UniSource.identity_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createMentorRequestDTO {
    private String name;
    private String email;
    private String password;
    private int organizationId;
}
