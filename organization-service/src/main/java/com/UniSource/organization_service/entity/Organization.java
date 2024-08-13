package com.UniSource.organization_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name="_organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean isVerified;
    private String description;
    private int identityId;
    private String public_id;
    private String public_url;
}
