package com.UniSource.organization_service.controller;

import com.UniSource.organization_service.dto.*;
import com.UniSource.organization_service.entity.Mentor;
import com.UniSource.organization_service.exception.CustomException;
import com.UniSource.organization_service.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mentor")
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<Mentor>> createMentor(
            @RequestBody createMentorRequestDTO request) {
        try{
            System.out.println(request.toString());
            Mentor newMentor=mentorService.saveMentor(request);
            ResponseDTO<Mentor> response = new ResponseDTO<>(true, newMentor, "Mentor registered is successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Mentor> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Mentor> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteMentor(@PathVariable int id) {
        try {
            mentorService.deleteMentor(id);
            ResponseDTO<Void> response = new ResponseDTO<>(true, null, "Mentor deleted successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<MentorDetails>> getMentorById(@PathVariable int id) {
        try {
            MentorDetails mentor = mentorService.getMentorById(id);
            ResponseDTO<MentorDetails> response = new ResponseDTO<>(true, mentor, "Mentor retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<MentorDetails> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<MentorDetails> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/identity/{id}")
    public ResponseEntity<ResponseDTO<MentorDetails>> getMentorByIdentityId(@PathVariable int id) {
        try {
            MentorDetails mentor = mentorService.getMentorByIdentityId(id);
            ResponseDTO<MentorDetails> response = new ResponseDTO<>(true, mentor, "Mentor retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<MentorDetails> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<MentorDetails> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping
    public ResponseEntity<ResponseDTO<List<MentorDetails>>> getAllMentors() {
        try {
            List<MentorDetails> mentors = mentorService.getAllMentors();
            ResponseDTO<List<MentorDetails>> response = new ResponseDTO<>(true, mentors, "Mentors retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<MentorDetails>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/upload-image")
    public ResponseEntity<ResponseDTO<MentorDetails>> updateProfileImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("public_id") String publicId,
            @RequestParam("identityId") int identityId) {
        try {
            imageRequestDTO request = new imageRequestDTO();
            request.setFile(file);
            request.setPublic_id(publicId);
            request.setIdentityId(identityId);

            MentorDetails user = mentorService.UpdateProfileImage(request);
            ResponseDTO<MentorDetails> response = new ResponseDTO<>(true, user, "Mentor Profile Image updated Successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<MentorDetails> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<MentorDetails> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ResponseDTO<List<MentorDetails>>> getMentorsByOrganizationId(@PathVariable int organizationId) {
        try {
            List<MentorDetails> mentors = mentorService.getMentorsByOrganizationId(organizationId);
            ResponseDTO<List<MentorDetails>> response = new ResponseDTO<>(true, mentors, "Mentors retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<List<MentorDetails>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<List<MentorDetails>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
