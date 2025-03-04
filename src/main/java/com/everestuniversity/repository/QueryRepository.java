package com.everestuniversity.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.everestuniversity.entity.QueryEntity;

@Repository
public interface QueryRepository extends JpaRepository<QueryEntity, UUID> {
	@Query(value = "select * from queries q where LOWER(q.title) LIKE LOWER(CONCAT(:prefix,'%'))", nativeQuery = true)
	Optional<List<QueryEntity>> findByQueryName(@Param("prefix") String prefix);
}
