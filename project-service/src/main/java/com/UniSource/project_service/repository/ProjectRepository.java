package com.UniSource.project_service.repository;

import com.UniSource.project_service.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Optional<Project> findById(int id);
}
