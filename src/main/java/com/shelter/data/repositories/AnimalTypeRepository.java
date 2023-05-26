package com.shelter.data.repositories;

import com.shelter.data.entities.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
    boolean existsByType(String type);

    AnimalType findByType(String type);
}
