package com.UniSource.organization_service.repository;


import com.UniSource.organization_service.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization,Integer> {
    Optional<Organization> findByIdentityId(int identityId);
}
