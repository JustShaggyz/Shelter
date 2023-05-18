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
import java.util.List;
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

    @Override
    public Walk returnFromWalk(Long walkId, String comment) {
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

        return walk;
    }

    @Override
    public List<Walk> getOngoingWalks() {
        List<Walk> walks = walkRepository.findByIsFinishedFalse()
                .orElseThrow(() -> new NoSuchElementException("No ongoing walks!"));
        return walks;
    }
}
