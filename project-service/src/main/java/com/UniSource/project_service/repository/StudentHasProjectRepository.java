package com.UniSource.project_service.repository;

import com.UniSource.project_service.entity.StudentHasProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentHasProjectRepository extends JpaRepository<StudentHasProject, Integer> {
}