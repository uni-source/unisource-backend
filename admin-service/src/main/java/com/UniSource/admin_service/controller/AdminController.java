package com.UniSource.admin_service.controller;
import com.UniSource.admin_service.dto.CreateRequestDTO;
import com.UniSource.admin_service.dto.RegisterDTO;
import com.UniSource.admin_service.entity.Admin;
import com.UniSource.admin_service.exception.CustomException;
import com.UniSource.admin_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody CreateRequestDTO request) {
        try {
            Admin admin = service.createAdmin(request);
            return ResponseEntity.ok(admin);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{identityId}")
    public ResponseEntity<RegisterDTO> getAdminByIdentityId(@PathVariable int identityId) {
        try {
            RegisterDTO admin = service.findByIdentityId(identityId);
            return ResponseEntity.ok(admin);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}