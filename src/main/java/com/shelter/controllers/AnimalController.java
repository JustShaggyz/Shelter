package com.shelter.controllers;

import com.shelter.dto.*;
import com.shelter.services.AnimalService;
import com.shelter.services.WalkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final WalkService walkService;

    @GetMapping
    public ResponseEntity<List<returnAnimalDTO>> getAllAnimals() {
        List<returnAnimalDTO> animals = animalService.getAllAnimals();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<returnDetailedAnimalDTO> getAnimal(@PathVariable Long animalId) {
        returnDetailedAnimalDTO animal = animalService.getAnimalById(animalId);
        return ResponseEntity.ok(animal);
    }

    @GetMapping("/available")
    public ResponseEntity<List<returnAnimalDTO>> getAvailableAnimals() {
        List<returnAnimalDTO> availableAnimals = animalService.getAvailableAnimals();
        return ResponseEntity.ok(availableAnimals);
    }

    @GetMapping("/onWalk")
    public ResponseEntity<List<returnAnimalDTO>> getAnimalsOutForWalk() {
        List<returnAnimalDTO> animals = animalService.getAnimalsOutForWalk();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/animal-types")
    public ResponseEntity<List<returnAnimalType>> getAnimalTypes() {
        List<returnAnimalType> types = animalService.getAnimalTypes();
        return ResponseEntity.ok(types);
    }

    @PostMapping("/add")
    public ResponseEntity<returnDetailedAnimalDTO> addAnimal(@Valid @ModelAttribute AnimalDTO animalDTO) {
        returnDetailedAnimalDTO savedAnimal = animalService.addAnimal(animalDTO);
        return ResponseEntity.ok(savedAnimal);
    }

    @PutMapping("/{animalId}/adopt")
    public ResponseEntity<returnDetailedAnimalDTO> markAsAdopted(@PathVariable Long animalId) {
        returnDetailedAnimalDTO adoptedAnimal = animalService.adopt(animalId);
        return ResponseEntity.ok(adoptedAnimal);
    }

    @PostMapping("/walk")
    public ResponseEntity<returnWalkDTO> takeAnimalForWalk(@RequestBody WalkDTO walkDTO) {
        returnWalkDTO walk = walkService.takeAnimalForWalk(walkDTO);
        return ResponseEntity.ok(walk);
    }

    @PutMapping("/walk/return/{walkId}")
    public ResponseEntity<returnWalkDTO> returnFromWalk(@PathVariable Long walkId, @RequestBody String comment) {
        returnWalkDTO walk = walkService.returnFromWalk(walkId, comment);
        return ResponseEntity.ok(walk);
    }

    @GetMapping("/walk/ongoing")
    public ResponseEntity<List<returnWalkDTO>> returnOngoingWalks() {
        List<returnWalkDTO> walks = walkService.getOngoingWalks();
        return ResponseEntity.ok(walks);
    }

    @PostMapping("/add-animal-type")
    public ResponseEntity<String> createAnimalType(@RequestBody String type) {
        boolean created = animalService.createAnimalType(type);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Animal type created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Animal type already exists");
        }
    }
}
