package com.UniSource.organization_service.service;


import com.UniSource.organization_service.client.IdentityClient;
import com.UniSource.organization_service.dto.IsVerifyDTO;
import com.UniSource.organization_service.dto.OrganizationDetailsDTO;
import com.UniSource.organization_service.dto.UpdateOrganizationRequestDTO;
import com.UniSource.organization_service.dto.imageRequestDTO;
import com.UniSource.organization_service.entity.Organization;
import com.UniSource.organization_service.exception.CustomException;
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
    public Organization UpdateDescription(UpdateOrganizationRequestDTO request) {
        Organization organization = repository.findByIdentityId(request.getIdentityId())
                .orElseThrow(() -> new CustomException("User not found"));
        organization.setDescription(request.getDescription());
        return repository.save(organization);
    }
    public OrganizationDetailsDTO getOrganizationById(int id) throws CustomException {

        try {
            var identityResponse = this.identityClient.getUserById(id);
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: " + id);
            }

            Optional<Organization> organization = repository.findByIdentityId(id);
            if (organization.isEmpty()) {
                throw new CustomException("Organization not found for id: " + id);
            }


            OrganizationDetailsDTO result = OrganizationDetailsDTO.build(
                    identityResponse.get().getData().getName(),
                    identityResponse.get().getData().getEmail(),
                    organization.get().isVerified(),
                    organization.get().getDescription(),
                    organization.get().getPublic_id(),
                    organization.get().getPublic_url(),
                    identityResponse.get().getData().getId(),
                    identityResponse.get().getData().getContact()
            );

            return result;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error retrieving organization by id: " + id, e);
        }

    }
    public OrganizationDetailsDTO UpdateProfileImage(imageRequestDTO request) {
        try {
            var identityResponse = this.identityClient.getUserById(request.getIdentityId());
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: " + request.getIdentityId());
            }

            Optional<Organization> organizationOpt = repository.findByIdentityId(request.getIdentityId());
            if (organizationOpt.isEmpty()) {
                throw new CustomException("Organization not found for id: " + request.getIdentityId());
            }

            Organization organization = organizationOpt.get();


            if (request.getPublic_id() != null && !request.getPublic_id().isEmpty()) {
                try {
                    cloudinaryService.deleteImage(request.getPublic_id());
                } catch (Exception e) {
                    throw new CustomException("Failed to delete existing image", "IMAGE_DELETE_ERROR");
                }
            }

            Map<String, String> uploadResult;
            try {
                uploadResult = cloudinaryService.uploadImage(request.getFile().getBytes());
            } catch (Exception e) {
                throw new CustomException("Failed to upload new image", "IMAGE_UPLOAD_ERROR");
            }

            String publicId = uploadResult.get("public_id");
            String publicUrl = uploadResult.get("secure_url");

            organization.setPublic_id(publicId);
            organization.setPublic_url(publicUrl);
            Organization updatedStudent = repository.save(organization);

            OrganizationDetailsDTO result = OrganizationDetailsDTO.build(
                    identityResponse.get().getData().getName(),
                    identityResponse.get().getData().getEmail(),
                    organization.isVerified(),
                    organization.getDescription(),
                    updatedStudent.getPublic_id(),
                    updatedStudent.getPublic_url(),
                    identityResponse.get().getData().getId(),
                    identityResponse.get().getData().getContact()
            );

            return result;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error retrieving organization by id: " + request.getIdentityId(), e);
        }

    }

    public Organization isVerify(IsVerifyDTO request){
        try {
            var identityResponse = this.identityClient.getUserById(request.getAdminIdentityId());
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: " + request.getAdminIdentityId());
            }
            if (!"ADMIN".equals(identityResponse.get().getData().getRole().toString())){
                throw new CustomException("Unauthorized Access");
            }
            Organization organization = repository.findById(request.getOrganizationId())
                    .orElseThrow(() -> new CustomException("Organization not found"));
            organization.setVerified( organization.isVerified());
            return repository.save(organization);
        }
        catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }
}

