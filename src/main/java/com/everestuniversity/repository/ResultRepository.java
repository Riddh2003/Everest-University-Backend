package com.everestuniversity.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.everestuniversity.entity.ResultEntity;
import com.everestuniversity.entity.StudentEntity;

public interface ResultRepository extends JpaRepository<ResultEntity, UUID> {
    List<ResultEntity> findByStudent(StudentEntity student);
}
