package com.shelter.data.repositories;

import com.shelter.data.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByIsAdoptedFalse();
    List<Animal> findByIsAvailableFalseAndIsAdoptedFalse();
    List<Animal> findByIsAvailableTrue();

    Optional<Animal> findByIdAndIsAdoptedFalse(Long id);
}
