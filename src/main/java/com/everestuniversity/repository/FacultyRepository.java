package com.everestuniversity.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.everestuniversity.entity.FacultyEntity;

public interface FacultyRepository extends JpaRepository<FacultyEntity, UUID> {

}
