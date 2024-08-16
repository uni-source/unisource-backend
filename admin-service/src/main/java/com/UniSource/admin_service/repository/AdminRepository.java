package com.UniSource.admin_service.repository;

import com.UniSource.admin_service.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByIdentityId(int identityId);
}