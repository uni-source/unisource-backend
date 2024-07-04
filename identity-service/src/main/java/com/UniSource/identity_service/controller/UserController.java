package com.UniSource.identity_service.controller;

import com.UniSource.identity_service.dto.ResetPasswordRequestDTO;
import com.UniSource.identity_service.dto.ResponseDTO;
import com.UniSource.identity_service.dto.UpdateUserRequestDTO;
import com.UniSource.identity_service.entity.User;
import com.UniSource.identity_service.exception.CustomException;
import com.UniSource.identity_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private AuthenticationService service;
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<User>> getUserById(@PathVariable int id) {
        try {
            User user = service.getUserById(id);
            ResponseDTO<User> response = new ResponseDTO<>(true, user, "User retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<User>>> getAllUsers() {
        try {
            List<User> users = service.getAllUsers();
            ResponseDTO<List<User>> response = new ResponseDTO<>(true, users, "Users retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<User>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<User>> updateUser(@RequestBody UpdateUserRequestDTO userDTO) {
        try {
            User user = service.UpdateUser(userDTO);
            ResponseDTO<User> response = new ResponseDTO<>(true, user, "User updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null,  e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResponseDTO<User>> resetPassword(@RequestBody ResetPasswordRequestDTO userDTO) {
        try {
            User user = service.RestPassword(userDTO);
            ResponseDTO<User> response = new ResponseDTO<>(true, user, "Password reset successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<User> response = new ResponseDTO<>(false, null, "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@PathVariable int id) {
        try {
            service.deleteUser(id);
            ResponseDTO<Void> response = new ResponseDTO<>(true, null, "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
