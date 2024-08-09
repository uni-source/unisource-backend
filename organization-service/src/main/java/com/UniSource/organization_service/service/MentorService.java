package com.UniSource.organization_service.service;

import com.UniSource.organization_service.client.IdentityClient;
import com.UniSource.organization_service.client.User;
import com.UniSource.organization_service.dto.*;
import com.UniSource.organization_service.entity.Mentor;
import com.UniSource.organization_service.entity.Organization;
import com.UniSource.organization_service.exception.CustomException;
import com.UniSource.organization_service.repository.MentorRepository;
import com.UniSource.organization_service.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepository;
    private final IdentityClient identityClient;
    private final OrganizationRepository organizationRepository;
    private final CloudinaryService cloudinaryService;

    public Mentor saveMentor(createMentorRequestDTO requestDTO){
        Optional<Organization> organization=organizationRepository.findByIdentityId(requestDTO.getOrganizationId());
        if (organization.isEmpty()) {
            throw new CustomException("Organization not found");
        }
        RegisterDTO registerDTO = new RegisterDTO(
                requestDTO.getName(),
                requestDTO.getEmail(),
                requestDTO.getPassword(),
                "MENTOR"
        );
        ResponseEntity<ResponseDTO<User>> responseEntity = identityClient.register(registerDTO);

        if (responseEntity.getStatusCode().isError() || responseEntity.getBody() == null) {
            throw new CustomException("Failed to register user");
        }

        ResponseDTO<User> responseDTO = responseEntity.getBody();
        User user = responseDTO.getData();
        int identityId = user.getId();

        Mentor mentor = Mentor.build(
                0,
                identityId,
                requestDTO.getOrganizationId(),
                "",
                ""
        );

        return mentorRepository.save(mentor);

    }

    public void deleteMentor(int mentorId) {
        if (!mentorRepository.existsById(mentorId)) {
            throw new CustomException("Mentor not found");
        }
        mentorRepository.deleteById(mentorId);
    }


    public MentorDetails getMentorById(int mentorId) {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new CustomException("Mentor not found"));
        var identityResponse = this.identityClient.getUserById(mentor.getIdentityId());
        if (identityResponse.isEmpty()) {
            throw new CustomException("Identity not found for id: " + mentor.getIdentityId());
        }


        return new MentorDetails(mentor.getId(), mentor.getIdentityId(), identityResponse.get().getData().getName(), identityResponse.get().getData().getEmail(), mentor.getOrganizationId(),mentor.getPublic_id(),mentor.getPublic_url());
    }

    public List<MentorDetails> getAllMentors() {
        List<Mentor> mentors = mentorRepository.findAll();
        return mentors.stream().map(mentor -> {
            var identityResponse = this.identityClient.getUserById(mentor.getIdentityId());
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: " + mentor.getIdentityId());
            }
            return new MentorDetails(mentor.getId(), mentor.getIdentityId(), identityResponse.get().getData().getName(), identityResponse.get().getData().getEmail(), mentor.getOrganizationId(),mentor.getPublic_id(),mentor.getPublic_url());
        }).collect(Collectors.toList());
    }

    public MentorDetails UpdateProfileImage(imageRequestDTO request) {
        try {
            var identityResponse = this.identityClient.getUserById(request.getIdentityId());
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: " + request.getIdentityId());
            }

            Optional<Mentor> mentorOpt = mentorRepository.findByIdentityId(request.getIdentityId());
            if (mentorOpt.isEmpty()) {
                throw new CustomException("Mentor not found for id: " + request.getIdentityId());
            }

            Mentor mentor = mentorOpt.get();

            if (request.getPublic_id() != null && !request.getPublic_id().isEmpty()) {
                try {
                    cloudinaryService.deleteImage(request.getPublic_id());
                } catch (Exception e) {
                    throw new CustomException("Failed to delete existing image", "IMAGE_DELETE_ERROR");
                }
            }

            Map<String, String> uploadResult;
            try {
                uploadResult = cloudinaryService.uploadMentorImage(request.getFile().getBytes());
            } catch (Exception e) {
                throw new CustomException("Failed to upload new image", "IMAGE_UPLOAD_ERROR");
            }

            String publicId = uploadResult.get("public_id");
            String publicUrl = uploadResult.get("secure_url");

            mentor.setPublic_id(publicId);
            mentor.setPublic_url(publicUrl);
            Mentor updatedMentor = mentorRepository.save(mentor);

            MentorDetails result = new MentorDetails(
                    updatedMentor.getId(), updatedMentor.getIdentityId(), identityResponse.get().getData().getName(), identityResponse.get().getData().getEmail(), updatedMentor.getOrganizationId(),updatedMentor.getPublic_id(),updatedMentor.getPublic_url()
            );

            return result;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error retrieving mentor by id: " + request.getIdentityId(), e);
        }
    }
}
