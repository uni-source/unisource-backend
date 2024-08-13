package com.UniSource.student_service.controller;

import com.UniSource.student_service.dto.*;
import com.UniSource.student_service.entity.Student;
import com.UniSource.student_service.exception.CustomException;
import com.UniSource.student_service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;
    @PostMapping
    public ResponseEntity<ResponseDTO<Student>> createStudent(
            @RequestBody createStudentDTO request) {
        try{
            Student newStudent=service.saveStudent(Student.build(0, false, 0, "", request.getIdentityId(),"",""));
            ResponseDTO<Student> response = new ResponseDTO<>(true, newStudent, "Student registered is successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Student> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Student> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<StudentDetailsDTO >> getUserById(@PathVariable int id) {
        try {
            StudentDetailsDTO student = service.getStudentById(id);
            ResponseDTO<StudentDetailsDTO > response = new ResponseDTO<>(true, student, "User retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<StudentDetailsDTO > response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<StudentDetailsDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping
    public ResponseEntity<ResponseDTO<Student>> updateUser(@RequestBody UpdateStudentRequestDTO request) {
        try {
            Student user = service.UpdateUser(request);
            ResponseDTO<Student> response = new ResponseDTO<>(true, user, "Student updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Student> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Student> response = new ResponseDTO<>(false, null,  e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/upload-image")
    public ResponseEntity<ResponseDTO<StudentDetailsDTO>> updateProfileImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("public_id") String publicId,
            @RequestParam("identityId") int identityId) {
        try {
            imageRequestDTO request = new imageRequestDTO();
            request.setFile(file);
            request.setPublic_id(publicId);
            request.setIdentityId(identityId);

            StudentDetailsDTO user = service.UpdateProfileImage(request);
            ResponseDTO<StudentDetailsDTO> response = new ResponseDTO<>(true, user, "Student Profile Image updated Successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<StudentDetailsDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<StudentDetailsDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/score")
    public ResponseEntity<ResponseDTO<Student>> updateScore(@RequestBody UpdateScoreRequestDTO request) {
        try {
            Student user = service.UpdateScore(request);
            ResponseDTO<Student> response = new ResponseDTO<>(true, user, "Score updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Student> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Student> response = new ResponseDTO<>(false, null,  e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/student-verify")
    public ResponseEntity<ResponseDTO<Student>> StudentVerification(@RequestBody IsVerifyDTO request) {
        try {
            Student user = service.isVerify(request);
            ResponseDTO<Student> response = new ResponseDTO<>(true, user, "Student Verified successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Student> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Student> response = new ResponseDTO<>(false, null,  e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
