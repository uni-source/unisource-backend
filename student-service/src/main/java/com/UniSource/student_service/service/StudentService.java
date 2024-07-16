package com.UniSource.student_service.service;

import com.UniSource.student_service.client.IdentityClient;
import com.UniSource.student_service.client.User;
import com.UniSource.student_service.dto.IsVerifyDTO;
import com.UniSource.student_service.dto.StudentDetailsDTO;
import com.UniSource.student_service.dto.UpdateScoreRequestDTO;
import com.UniSource.student_service.dto.UpdateStudentRequestDTO;
import com.UniSource.student_service.entity.Student;
import com.UniSource.student_service.exception.CustomException;
import com.UniSource.student_service.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final IdentityClient identityClient;

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
                    student.get().getDescription()
            );

            return result;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Error retrieving student by id: " + id, e);
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

