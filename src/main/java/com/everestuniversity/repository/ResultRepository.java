package com.everestuniversity.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.everestuniversity.entity.ResultEntity;

public interface ResultRepository extends JpaRepository<ResultEntity, UUID> {

}
