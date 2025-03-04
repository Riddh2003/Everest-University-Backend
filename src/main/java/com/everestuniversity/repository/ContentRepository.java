package com.everestuniversity.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everestuniversity.entity.ContentEntity;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, UUID> {

}
