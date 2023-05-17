package com.shelter.services.implementations;

import com.shelter.data.entities.User;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.data.repositories.UserRepository;
import com.shelter.data.repositories.WalkRepository;
import com.shelter.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final WalkRepository walkRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
