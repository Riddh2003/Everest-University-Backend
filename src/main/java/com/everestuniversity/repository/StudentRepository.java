package com.everestuniversity.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everestuniversity.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    Optional<StudentEntity> findByEmail(String email);

    Optional<StudentEntity> findByEnrollmentId(String enrollmentId);

}
