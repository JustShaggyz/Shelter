package com.shelter.controllers;

import com.shelter.data.entities.Animal;
import com.shelter.data.entities.Walk;
import com.shelter.dto.AnimalDTO;
import com.shelter.dto.WalkDTO;
import com.shelter.services.AnimalService;
import com.shelter.services.WalkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final WalkService walkService;

    @GetMapping
    public ResponseEntity<List<Animal>> getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<Animal> getAnimal(@PathVariable Long animalId) {
        Animal animal = animalService.getAnimalById(animalId);
        return ResponseEntity.ok(animal);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Animal>> getAvailableAnimals() {
        List<Animal> availableAnimals = animalService.getAvailableAnimals();
        return ResponseEntity.ok(availableAnimals);
    }

    @GetMapping("/onWalk")
    public ResponseEntity<List<Animal>> getAnimalsOutForWalk() {
        List<Animal> animals = animalService.getAnimalsOutForWalk();
        return ResponseEntity.ok(animals);
    }

    @PostMapping("/add")
    public ResponseEntity<Animal> addAnimal(@Valid @ModelAttribute AnimalDTO animalDTO) {
        Animal savedAnimal = animalService.addAnimal(animalDTO);
        return ResponseEntity.ok(savedAnimal);
    }

    @PutMapping("/{animalId}/adopt")
    public ResponseEntity<Animal> markAsAdopted(@PathVariable Long animalId) {
        Animal adoptedAnimal = animalService.adopt(animalId);
        return ResponseEntity.ok(adoptedAnimal);
    }

    @PostMapping("/walk")
    public ResponseEntity<Walk> takeAnimalForWalk(@RequestBody WalkDTO walkDTO) {
        Walk walk = walkService.takeAnimalForWalk(walkDTO);
        return ResponseEntity.ok(walk);
    }

    @PutMapping("/walk/return/{walkId}")
    public ResponseEntity<Walk> returnFromWalk(@PathVariable Long walkId, @RequestBody String comment) {
        Walk walk = walkService.returnFromWalk(walkId, comment);
        return ResponseEntity.ok(walk);
    }

    @GetMapping("walk/ongoing")
    public ResponseEntity<List<Walk>> returnOngoingWalks() {
        List<Walk> walks = walkService.getOngoingWalks();
        return ResponseEntity.ok(walks);
    }


}
