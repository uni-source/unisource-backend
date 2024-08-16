package com.UniSource.identity_service.client;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mentor {

    private int id;
    private int identityId;
    private int organizationId;
    private String public_id ;
    private String public_url;
}
