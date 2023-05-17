package com.shelter.data.repositories;

import com.shelter.data.entities.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalkRepository extends JpaRepository<Walk, Long> {
    Optional<Walk> findByIdAndIsFinishedFalse(@Param("id") Long id);
}
