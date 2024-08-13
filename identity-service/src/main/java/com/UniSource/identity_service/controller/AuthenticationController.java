package com.UniSource.identity_service.controller;

import com.UniSource.identity_service.dto.LoginDTO;
import com.UniSource.identity_service.dto.LoginResponseDTO;
import com.UniSource.identity_service.dto.RegisterDTO;
import com.UniSource.identity_service.dto.ResponseDTO;
import com.UniSource.identity_service.entity.User;
import com.UniSource.identity_service.exception.CustomException;
import com.UniSource.identity_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<User>> register(
            @RequestBody RegisterDTO request
    ) {
        try{
            User user=service.saveUser(User.build(
                    0,
                    request.getName(),
                    request.getEmail(),
                    request.getRole(),
                    passwordEncoder.encode(request.getPassword())
            ));
            ResponseDTO<User> response = new ResponseDTO<>(true, user, "User registered is successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> login(
            @RequestBody LoginDTO request
    ) {
        try{
            LoginResponseDTO token=service.login(request);
            ResponseDTO<LoginResponseDTO> response = new ResponseDTO<>(true, token, "User logged in successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<LoginResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<LoginResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
