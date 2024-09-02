package com.UniSource.project_service.repository;

import com.UniSource.project_service.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Integer> {
    List<Proposal> findByStudentId(int studentId);
    List<Proposal> findByProjectId(int projectId);
    List<Proposal> findByOrganizationId(int organizationId);
    List<Proposal> findByMentorId(int mentorId);

}