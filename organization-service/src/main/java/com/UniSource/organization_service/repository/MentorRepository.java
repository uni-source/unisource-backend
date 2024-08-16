package com.UniSource.organization_service.repository;
import com.UniSource.organization_service.entity.Mentor;
import com.UniSource.organization_service.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MentorRepository extends JpaRepository<Mentor,Integer> {
    Optional<Mentor> findByIdentityId(int identityId);
    List<Mentor> findByOrganizationId(int organizationId);
}
