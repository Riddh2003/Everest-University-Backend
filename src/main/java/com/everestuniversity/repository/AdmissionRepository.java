package com.everestuniversity.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everestuniversity.entity.AdmisstionEntity;

@Repository
public interface AdmissionRepository extends JpaRepository<AdmisstionEntity, UUID> {
	Optional<AdmisstionEntity> findByEmail(String email);
}
