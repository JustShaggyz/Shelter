package com.shelter.services;

import com.shelter.dto.AnimalDTO;
import com.shelter.dto.returnAnimalDTO;
import com.shelter.dto.returnDetailedAnimalDTO;

import java.util.List;

public interface AnimalService {

    List<returnAnimalDTO> getAllAnimals();

    returnDetailedAnimalDTO addAnimal(AnimalDTO animalDTO);

    returnDetailedAnimalDTO adopt(Long animalId);

    List<returnAnimalDTO> getAnimalsOutForWalk();

    returnDetailedAnimalDTO getAnimalById(Long animalId);
    List<returnAnimalDTO> getAvailableAnimals();

    void returnAnimalFromWalk(Long animalId);
}
