package com.UniSource.project_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDetailsDTO {
    private String name;
    private String email;
    private boolean isVerified;
    private String description;
    private String public_id;
    private String public_url;
    private int identityId;
    private String contact;
}
