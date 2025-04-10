package com.everestuniversity.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everestuniversity.entity.FacultyEntity;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyEntity, UUID> {
    Optional<FacultyEntity> findByEmail(String email);
}
