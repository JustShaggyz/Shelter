package com.shelter.services.implementations;

import com.shelter.data.entities.Animal;
import com.shelter.data.entities.User;
import com.shelter.data.entities.Walk;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.data.repositories.UserRepository;
import com.shelter.data.repositories.WalkRepository;
import com.shelter.dto.WalkDTO;
import com.shelter.services.WalkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class WalkServiceImplementation implements WalkService {


    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final WalkRepository walkRepository;

    @Override
    public Walk takeAnimalForWalk(WalkDTO walkDTO) {
        User user = userRepository.findById(walkDTO.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Animal animal = animalRepository.findById(walkDTO.getAnimalId())
                .orElseThrow(() -> new NoSuchElementException("Animal not found"));

        animal.setAvailable(false);
        animalRepository.save(animal);

        Walk walk = new Walk(user, animal, LocalDate.now(), false);
        return walkRepository.save(walk);
    }
}
