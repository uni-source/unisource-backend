package com.UniSource.project_service.repository;

import com.UniSource.project_service.entity.StudentHasProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentHasProjectRepository extends JpaRepository<StudentHasProject, Integer> {
    List<StudentHasProject> findByStudentId(int studentId);
    Optional<StudentHasProject> findByProjectId(int projectId);
}