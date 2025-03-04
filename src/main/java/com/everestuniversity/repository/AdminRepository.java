package com.everestuniversity.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everestuniversity.entity.AdminEntity;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {

	Optional<AdminEntity> findByEmail(String email);

}
