package com.shelter.services.implementations;

import com.shelter.data.entities.Animal;
import com.shelter.data.entities.User;
import com.shelter.data.entities.Walk;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.data.repositories.UserRepository;
import com.shelter.data.repositories.WalkRepository;
import com.shelter.dto.*;
import com.shelter.services.AnimalService;
import com.shelter.services.UserService;
import com.shelter.services.WalkService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalkServiceImplementation implements WalkService {


    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final WalkRepository walkRepository;
    private final UserService userService;
    private final AnimalService animalService;
    private final ModelMapper modelMapper;

    @Override
    public returnWalkDTO takeAnimalForWalk(WalkDTO walkDTO) {
        User user = userRepository.findById(walkDTO.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Animal animal = animalRepository.findById(walkDTO.getAnimalId())
                .orElseThrow(() -> new NoSuchElementException("Animal not found"));

        animal.setAvailable(false);
        animalRepository.save(animal);

        Walk walk = new Walk(user, animal, LocalDate.now(), false);
        walkRepository.save(walk);
        returnWalkDTO returnWalk = new returnWalkDTO(
                modelMapper.map(walk.getUser(), returnUserDTO.class),
                modelMapper.map(walk.getAnimal(), returnDetailedAnimalDTO.class),
                walk.getDate());
        return returnWalk;


    }

    @Override
    public returnWalkDTO returnFromWalk(Long walkId, String comment) {
        Walk walk = walkRepository.findByIdAndIsFinishedFalse(walkId)
                .orElseThrow(() -> new NoSuchElementException("Walk not found"));
        walk.setFinished(true);
        walkRepository.save(walk);

        Animal animal = walk.getAnimal();
        animal.setAvailable(true);
        animalRepository.save(animal);

        User user = walk.getUser();
        List<String> comments = user.getComments();
        comments.add(comment);
        user.setComments(comments);
        userRepository.save(user);

        return modelMapper.map(walk, returnWalkDTO.class);
    }

    @Override
    public List<returnWalkDTO> getOngoingWalks() {
        return walkRepository.findByIsFinishedFalse()
                .orElseThrow(() -> new NoSuchElementException("No ongoing walks!"))
                .stream()
                .map(walks -> modelMapper.map(walks, returnWalkDTO.class))
                .collect(Collectors.toList());
    }

}
