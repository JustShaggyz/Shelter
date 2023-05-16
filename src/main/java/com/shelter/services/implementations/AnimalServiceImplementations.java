package com.shelter.services.implementations;

import com.shelter.data.entities.Animal;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.dto.AnimalDTO;
import com.shelter.services.AnimalService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AnimalServiceImplementations implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;

    public Animal addAnimal(AnimalDTO animalDTO) {
        Animal animal = modelMapper.map(animalDTO, Animal.class);
        return animalRepository.save(animal);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findByIsAdoptedFalse();
    }

    public Animal adopt(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NoSuchElementException("Animal not found"));

        animal.setAdopted(true);
        return animalRepository.save(animal);
    }

    @Override
    public List<Animal> getAnimalsOutForWalk() {
        return animalRepository.findByIsAvailableFalseAndIsAdoptedFalse();
    }

}
