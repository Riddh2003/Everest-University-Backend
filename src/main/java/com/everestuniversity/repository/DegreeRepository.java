package com.everestuniversity.repository;

import com.everestuniversity.entity.DegreeEntity;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreeRepository extends JpaRepository<DegreeEntity, UUID> {

  Optional<DegreeEntity> findByDegreeName(String degreeName);

}
