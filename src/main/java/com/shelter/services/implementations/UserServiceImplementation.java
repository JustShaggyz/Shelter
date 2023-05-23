package com.shelter.services.implementations;

import com.shelter.data.entities.Role;
import com.shelter.data.entities.User;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.data.repositories.UserRepository;
import com.shelter.data.repositories.WalkRepository;
import com.shelter.dto.HistoryAndCommentsDTO;
import com.shelter.dto.returnUserDTO;
import com.shelter.dto.returnDetailedUserDTO;
import com.shelter.dto.returnWalkDTO;
import com.shelter.exceptions.UserNotFoundException;
import com.shelter.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final WalkRepository walkRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<returnUserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, returnUserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<returnUserDTO> getVolunteers() {
        return userRepository.findUsersByRole(Role.USER)
                .stream()
                .map(user -> modelMapper.map(user, returnUserDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public returnDetailedUserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return modelMapper.map(user, returnDetailedUserDTO.class);
    }


    public List<returnWalkDTO> getUserHistory(Long userId) {

        return getHistoryByUserIdAndDate(userId);
    }

    public List<String> getUserComments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<String> comments = user.getComments();

        int totalComments = comments.size();
        int startIndex = Math.max(totalComments - 5, 0); // Starting index to retrieve comments

        List<String> lastFiveComments = comments.subList(startIndex, totalComments);
        return lastFiveComments;
    }

    @Override
    public HistoryAndCommentsDTO getUserHistoryAndComments(Long userId) {

        List<returnWalkDTO> walks = getHistoryByUserIdAndDate(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<String> comments = user.getComments();

        HistoryAndCommentsDTO dto = new HistoryAndCommentsDTO(walks, comments);

        return dto;
    }

    private List<returnWalkDTO> getHistoryByUserIdAndDate(Long userId) {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        return walkRepository.findByUserIdAndDateAfter(userId, threeMonthsAgo)
                .orElseThrow(() -> new NoSuchElementException("History not found"))
                .stream()
                .map(walk -> modelMapper.map(walk, returnWalkDTO.class))
                .collect(Collectors.toList());
    }
}
