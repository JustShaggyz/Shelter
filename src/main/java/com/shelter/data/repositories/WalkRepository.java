package com.shelter.data.repositories;

import com.shelter.data.entities.Walk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkRepository extends JpaRepository<Walk, Long> {
}
