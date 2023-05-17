package com.shelter.services;

import com.shelter.data.entities.Animal;
import com.shelter.dto.AnimalDTO;

import java.util.List;

public interface AnimalService {

    List<Animal> getAllAnimals();

    Animal addAnimal(AnimalDTO animalDTO);

    Animal adopt(Long animalId);

    List<Animal> getAnimalsOutForWalk();

    Animal getAnimalById(Long animalId);
}
