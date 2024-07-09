package com.UniSource.identity_service.service;

import com.UniSource.identity_service.client.StudentClient;
import com.UniSource.identity_service.config.JwtService;
import com.UniSource.identity_service.dto.LoginDTO;
import com.UniSource.identity_service.dto.LoginResponseDTO;
import com.UniSource.identity_service.dto.ResetPasswordRequestDTO;
import com.UniSource.identity_service.dto.UpdateUserRequestDTO;
import com.UniSource.identity_service.entity.User;
import com.UniSource.identity_service.exception.CustomException;
import com.UniSource.identity_service.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserCredentialRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StudentClient studentClient;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new CustomException("Email is already taken");
        }
        User newUser =repository.save(user);
        String jwtToken = jwtService.generateToken(newUser);
        if (Objects.equals("STUDENT", newUser.getRole().toString())) {
            try{
                studentClient.createStudent(newUser.getId(), "Bearer " + jwtToken);
            }catch (Exception e){
                throw new CustomException(e.getMessage());
            }

        }
        return newUser;
    }

    public LoginResponseDTO login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return  new LoginResponseDTO(jwtToken, user);
    }

    public User getUserById(int id) {
        return repository.findById(id).orElseThrow(() -> new CustomException("User not found"));
    }
    public List<User> getAllUsers() {
        return repository.findAll();
    }
    public User UpdateUser( UpdateUserRequestDTO userDTO) {
        User user = repository.findById(userDTO.getId())
                .orElseThrow(() -> new CustomException("User not found"));

        user.setName(userDTO.getName());
        return repository.save(user);
    }
    public User RestPassword( ResetPasswordRequestDTO userDTO) {
        User user = repository.findById(userDTO.getId())
                .orElseThrow(() -> new CustomException("User not found"));

        if (!passwordEncoder.matches(userDTO.getOldPassword(), user.getPassword())) {
            throw new CustomException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return repository.save(user);
    }

    public void deleteUser(int id) {
        User user = repository.findById(id).orElseThrow(() -> new CustomException("User not found"));
        repository.delete(user);
    }


}
