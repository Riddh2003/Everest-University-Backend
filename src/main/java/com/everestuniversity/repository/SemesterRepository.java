package com.everestuniversity.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everestuniversity.entity.SemesterEntity;

@Repository
public interface SemesterRepository extends JpaRepository<SemesterEntity, UUID> {

    List<SemesterEntity> findByDegree_DegreeId(UUID uuid);

    Long countByDegree_DegreeId(UUID uuid);

}
