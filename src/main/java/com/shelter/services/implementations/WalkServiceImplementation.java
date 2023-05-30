package com.shelter.services.implementations;

import com.shelter.config.auth.AuthenticationFacade;
import com.shelter.data.entities.Animal;
import com.shelter.data.entities.Comment;
import com.shelter.data.entities.User;
import com.shelter.data.entities.Walk;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.data.repositories.UserRepository;
import com.shelter.data.repositories.WalkRepository;
import com.shelter.dto.*;
import com.shelter.exceptions.AnimalNotFoundException;
import com.shelter.exceptions.UserNotFoundException;
import com.shelter.exceptions.WalkNotFoundException;
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
    private final AuthenticationFacade authenticationFacade;

    //Take out for a walk
    @Override
    public returnWalkDTO takeAnimalForWalk(WalkDTO walkDTO) {
        //Get user by id
        User user = userRepository.findById(walkDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        //Get animal by id
        Animal animal = animalRepository.findById(walkDTO.getAnimalId())
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found"));

        //Set as non available
        animal.setAvailable(false);
        animalRepository.save(animal);

        //Create walk object with data for user, animal, date and status for completion
        Walk walk = new Walk(user, animal, LocalDate.now(), false);
        walkRepository.save(walk);
        return new returnWalkDTO(
                walk.getId(),
                modelMapper.map(walk.getUser(), returnUserDTO.class),
                modelMapper.map(walk.getAnimal(), returnDetailedAnimalDTO.class),
                walk.getDate(),
                walk.isFinished());


    }

    //Return from a walk
    @Override
    public returnWalkDTO returnFromWalk(Long walkId, String comment) {
        //Get walk by id
        Walk walk = walkRepository.findByIdAndIsFinishedFalse(walkId)
                .orElseThrow(() -> new WalkNotFoundException("Walk not found"));
        //Set status finished
        walk.setFinished(true);
        walkRepository.save(walk);

        //Set animal as available
        Animal animal = walk.getAnimal();
        animal.setAvailable(true);
        animalRepository.save(animal);

        //Add comment if existent
        if(comment != null) {
            User user = walk.getUser();
            List<Comment> comments = user.getComments();
            User currentUser = authenticationFacade.getCurrentUser();
            comments.add(new Comment(currentUser.getId(), user.getId(), walkId, comment));
            user.setComments(comments);
            userRepository.save(user);
        }


        return modelMapper.map(walk, returnWalkDTO.class);
    }

    @Override
    public List<returnWalkDTO> getOngoingWalks() {
        //Get ongoing walks by status and map to dto
        return walkRepository.findByIsFinishedFalse()
                .orElseThrow(() -> new WalkNotFoundException("No ongoing walks!"))
                .stream()
                .map(walks -> modelMapper.map(walks, returnWalkDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public returnWalkDTO getWalkById(Long walkId) {
        //Get walk by id and map to dto
        Walk walk = walkRepository.findById(walkId)
                .orElseThrow(() -> new WalkNotFoundException("Walk not found"));
        return modelMapper.map(walk, returnWalkDTO.class);
    }

}
