package com.everestuniversity.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everestuniversity.entity.StudentProfileEntity;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfileEntity, UUID> {

  Optional<StudentProfileEntity> findByStudent_StudentId(UUID studentId);

}
