package com.shelter.data.repositories;

import com.shelter.data.entities.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WalkRepository extends JpaRepository<Walk, Long> {
    Optional<Walk> findByIdAndIsFinishedFalse(@Param("id") Long id);

    Optional<List<Walk>> findByUserIdAndDateAfter(Long userId, LocalDate threeMonthsAgo);

    Optional<List<Walk>> findByIsFinishedFalse();
}
