package com.UniSource.organization_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorDetails {
    private int id;
    private int identityId;
    private String name;
    private String email;
    private String contact;
    private int organizationId;
    private String public_id ;
    private String public_url;

}
