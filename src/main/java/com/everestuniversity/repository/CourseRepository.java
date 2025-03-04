package com.everestuniversity.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.everestuniversity.entity.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findByCourseCode(String courseCode);
    List<CourseEntity> findBySemester_SemesterId(UUID degreeId);
}
