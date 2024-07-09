package com.UniSource.student_service.repository;

import com.UniSource.student_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    Optional<Student> findByIdentityId(int identityId);
}
