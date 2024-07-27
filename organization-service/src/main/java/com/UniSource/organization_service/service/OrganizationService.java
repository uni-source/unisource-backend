package com.UniSource.organization_service.service;


import com.UniSource.organization_service.client.IdentityClient;
import com.UniSource.organization_service.entity.Organization;
import com.UniSource.organization_service.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository repository;
    private final IdentityClient identityClient;
    private final CloudinaryService cloudinaryService;
    public Organization saveOrganization(Organization organization) {
        return repository.save(organization);
    }
}

