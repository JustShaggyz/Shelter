package com.shelter.controllers;

import com.shelter.data.entities.Animal;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.dto.AnimalDTO;
import com.shelter.services.AnimalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalRepository animalRepository;
    private final AnimalService animalService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Animal>> getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/out_for_walk")
    public ResponseEntity<List<Animal>> getAnimalsOutForWalk() {
        List<Animal> animals = animalService.getAnimalsOutForWalk();
        return ResponseEntity.ok(animals);
    }

    @PostMapping("/add")
    public ResponseEntity<Animal> addAnimal(@Valid @RequestBody AnimalDTO animalDTO) {
        Animal savedAnimal = animalService.addAnimal(animalDTO);
        return ResponseEntity.ok(savedAnimal);
    }

    @PutMapping("/{animalId}/adopt")
    public ResponseEntity<?> markAsAdopted(@PathVariable Long animalId) {
        Animal adoptedAnimal = animalService.adopt(animalId);
        return ResponseEntity.ok(adoptedAnimal);
    }
}
