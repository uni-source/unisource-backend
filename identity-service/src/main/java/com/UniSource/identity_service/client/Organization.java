package com.UniSource.identity_service.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    private int id;
    private boolean verifiedOrganization;
    private String description;
    private int identityId;
}
