package com.UniSource.student_service.service;

import com.UniSource.student_service.client.IdentityClient;
import com.UniSource.student_service.client.User;
import com.UniSource.student_service.dto.IsVerifyDTO;
import com.UniSource.student_service.dto.StudentDetailsDTO;
import com.UniSource.student_service.dto.UpdateScoreRequestDTO;
import com.UniSource.student_service.dto.UpdateStudentRequestDTO;
import com.UniSource.student_service.dto.imageRequestDTO;
import com.UniSource.student_service.entity.Student;
import com.UniSource.student_service.exception.CustomException;
import com.UniSource.student_service.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final IdentityClient identityClient;
    private final CloudinaryService cloudinaryService;
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    public Student UpdateUser(UpdateStudentRequestDTO request) {
        Student student = repository.findByIdentityId(request.getIdentityId())
                .orElseThrow(() -> new CustomException("User not found"));
        student.setDescription(request.getDescription());
        return repository.save(student);
    }

    public StudentDetailsDTO getStudentById(int id) throws CustomException {

        try {
            var identityResponse = this.identityClient.getUserById(id);
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: " + id);
            }

            Optional<Student> student = repository.findByIdentityId(id);
            if (student.isEmpty()) {
                throw new CustomException("Student not found for id: " + id);
            }


            StudentDetailsDTO result = StudentDetailsDTO.build(
                    identityResponse.get().getData().getName(),
                    identityResponse.get().getData().getEmail(),
                    student.get().isVerified(),
                    student.get().getScore(),
                    student.get().getDescription(),
                    student.get().getPublic_id(),
                    student.get().getPublic_url(),
                    identityResponse.get().getData().getId()
            );

            return result;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error retrieving student by id: " + id, e);
        }

    }


    public StudentDetailsDTO UpdateProfileImage(imageRequestDTO request) {
        try {
            var identityResponse = this.identityClient.getUserById(request.getIdentityId());
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: " + request.getIdentityId());
            }

            Optional<Student> studentOpt = repository.findByIdentityId(request.getIdentityId());
            if (studentOpt.isEmpty()) {
                throw new CustomException("Student not found for id: " + request.getIdentityId());
            }

            Student student = studentOpt.get();
            System.out.println("Student found: " + student.getIdentityId());

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

            student.setPublic_id(publicId);
            student.setPublic_url(publicUrl);
            Student updatedStudent = repository.save(student);

            StudentDetailsDTO result = StudentDetailsDTO.build(
                    identityResponse.get().getData().getName(),
                    identityResponse.get().getData().getEmail(),
                    student.isVerified(),
                    student.getScore(),
                    student.getDescription(),
                    updatedStudent.getPublic_id(),
                    updatedStudent.getPublic_url(),
                    identityResponse.get().getData().getId()
            );

            return result;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error retrieving student by id: " + request.getIdentityId(), e);
        }
    }
    public Student UpdateScore(UpdateScoreRequestDTO request) {
        Student student = repository.findByIdentityId(request.getIdentityId())
                .orElseThrow(() -> new CustomException("User not found"));
        //int score=student.getScore();
        //score+= request.getScore();
        student.setScore(student.getScore()+ request.getScore());
        return repository.save(student);
    }
    public Student isVerify(IsVerifyDTO request){
        try {
            var identityResponse = this.identityClient.getUserById(request.getAdminIdentityId());
            if (identityResponse.isEmpty()) {
                throw new CustomException("Identity not found for id: " + request.getAdminIdentityId());
            }
            if (!"ADMIN".equals(identityResponse.get().getData().getRole().toString())){
                throw new CustomException("Unauthorized Access");
            }
            Student student = repository.findById(request.getStudentId())
                    .orElseThrow(() -> new CustomException("Student not found"));
            student.setVerified(student.isVerified());
            return repository.save(student);
        }
        catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }
}

