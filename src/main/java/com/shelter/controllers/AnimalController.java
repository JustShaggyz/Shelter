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
@RequestMapping()
public class AnimalController {

    private final AnimalService animalService;
    private final WalkService walkService;

    @GetMapping("/all-animals")
    public ResponseEntity<List<returnAnimalDTO>> getAllAnimals() {
        List<returnAnimalDTO> animals = animalService.getAllAnimals();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/all-animals/{animalId}")
    public ResponseEntity<returnDetailedAnimalDTO> getAnimal(@PathVariable Long animalId) {
        returnDetailedAnimalDTO animal = animalService.getAnimalById(animalId);
        return ResponseEntity.ok(animal);
    }

    @GetMapping("/animals/available")
    public ResponseEntity<List<returnAnimalDTO>> getAvailableAnimals() {
        List<returnAnimalDTO> availableAnimals = animalService.getAvailableAnimals();
        return ResponseEntity.ok(availableAnimals);
    }

    @GetMapping("/animals/onWalk")
    public ResponseEntity<List<returnAnimalDTO>> getAnimalsOutForWalk() {
        List<returnAnimalDTO> animals = animalService.getAnimalsOutForWalk();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/animals/animal-types")
    public ResponseEntity<List<returnAnimalType>> getAnimalTypes() {
        List<returnAnimalType> types = animalService.getAnimalTypes();
        return ResponseEntity.ok(types);
    }

    @PostMapping("/animals/add")
    public ResponseEntity<returnDetailedAnimalDTO> addAnimal(@Valid @ModelAttribute AnimalDTO animalDTO) {
        returnDetailedAnimalDTO savedAnimal = animalService.addAnimal(animalDTO);
        return ResponseEntity.ok(savedAnimal);
    }

    @PutMapping("/animals/{animalId}/adopt")
    public ResponseEntity<returnDetailedAnimalDTO> markAsAdopted(@PathVariable Long animalId) {
        returnDetailedAnimalDTO adoptedAnimal = animalService.adopt(animalId);
        return ResponseEntity.ok(adoptedAnimal);
    }

    @PostMapping("/animals/walk")
    public ResponseEntity<returnWalkDTO> takeAnimalForWalk(@RequestBody WalkDTO walkDTO) {
        returnWalkDTO walk = walkService.takeAnimalForWalk(walkDTO);
        return ResponseEntity.ok(walk);
    }

    @PutMapping("/animals/walk/return/{walkId}")
    public ResponseEntity<returnWalkDTO> returnFromWalk(@PathVariable Long walkId, @RequestBody String comment) {
        returnWalkDTO walk = walkService.returnFromWalk(walkId, comment);
        return ResponseEntity.ok(walk);
    }

    @GetMapping("/animals/walk/ongoing")
    public ResponseEntity<List<returnWalkDTO>> returnOngoingWalks() {
        List<returnWalkDTO> walks = walkService.getOngoingWalks();
        return ResponseEntity.ok(walks);
    }

    @PostMapping("/animals/add-animal-type")
    public ResponseEntity<String> createAnimalType(@RequestBody String type) {
        boolean created = animalService.createAnimalType(type);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Animal type created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Animal type already exists");
        }
    }
}
