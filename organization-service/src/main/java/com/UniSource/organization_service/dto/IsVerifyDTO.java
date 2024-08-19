package com.UniSource.organization_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsVerifyDTO {
    private int adminIdentityId;
    private int organizationId;
    private boolean verifiedOrganization;
}
