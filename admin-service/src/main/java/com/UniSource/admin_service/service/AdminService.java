package com.UniSource.admin_service.service;

import com.UniSource.admin_service.client.IdentityClient;
import com.UniSource.admin_service.dto.CreateRequestDTO;
import com.UniSource.admin_service.dto.RegisterDTO;
import com.UniSource.admin_service.entity.Admin;
import com.UniSource.admin_service.exception.CustomException;
import com.UniSource.admin_service.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final IdentityClient identityClient;
    private final AdminRepository repository;
    public Admin createAdmin(CreateRequestDTO dto){
        try {
            var identityResponse = this.identityClient.getUserById(dto.getIdentityId());
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: ");
            }
            Admin result =Admin.build(0,dto.getIdentityId());
            return repository.save(result);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error retrieving organization by id: " );
        }
    }

    public RegisterDTO findByIdentityId(int identityId) throws CustomException {
        Admin admin = repository.findByIdentityId(identityId)
                .orElseThrow(() -> new CustomException("Admin not found for identityId: " + identityId));
        var identityResponse = this.identityClient.getUserById(identityId);
        if (identityResponse.isEmpty()) {
            throw new CustomException("Identity not found for id: ");
        }
        RegisterDTO result=new RegisterDTO(
                identityResponse.get().getData().getName(),
                identityResponse.get().getData().getEmail(),
                identityResponse.get().getData().getPassword(),
                identityResponse.get().getData().getRole()
        );
        return result;
    }

}
