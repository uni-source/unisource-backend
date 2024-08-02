package com.UniSource.organization_service.service;

import com.UniSource.organization_service.client.IdentityClient;
import com.UniSource.organization_service.client.User;
import com.UniSource.organization_service.dto.MentorDetails;
import com.UniSource.organization_service.dto.RegisterDTO;
import com.UniSource.organization_service.dto.ResponseDTO;
import com.UniSource.organization_service.dto.createMentorRequestDTO;
import com.UniSource.organization_service.entity.Mentor;
import com.UniSource.organization_service.entity.Organization;
import com.UniSource.organization_service.exception.CustomException;
import com.UniSource.organization_service.repository.MentorRepository;
import com.UniSource.organization_service.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MentorService {
    private final MentorRepository mentorRepository;
    private final IdentityClient identityClient;
    private final OrganizationRepository organizationRepository;

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
                requestDTO.getOrganizationId()
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


        return new MentorDetails(mentor.getId(), mentor.getIdentityId(), identityResponse.get().getData().getName(), identityResponse.get().getData().getEmail(), mentor.getOrganizationId());
    }

    public List<MentorDetails> getAllMentors() {
        List<Mentor> mentors = mentorRepository.findAll();
        return mentors.stream().map(mentor -> {
            var identityResponse = this.identityClient.getUserById(mentor.getIdentityId());
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: " + mentor.getIdentityId());
            }
            return new MentorDetails(mentor.getId(), mentor.getIdentityId(), identityResponse.get().getData().getName(), identityResponse.get().getData().getEmail(), mentor.getOrganizationId());
        }).collect(Collectors.toList());
    }
}
