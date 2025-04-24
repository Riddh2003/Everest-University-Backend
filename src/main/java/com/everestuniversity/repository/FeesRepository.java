package com.everestuniversity.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everestuniversity.entity.FeesEntity;

@Repository
public interface FeesRepository extends JpaRepository<FeesEntity, UUID> {
    Optional<FeesEntity> findByStudentEnrollmentId(String enrollmentId);
}