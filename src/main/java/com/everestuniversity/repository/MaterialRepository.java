package com.everestuniversity.repository;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.everestuniversity.entity.MaterialEntity;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, UUID> {

}
