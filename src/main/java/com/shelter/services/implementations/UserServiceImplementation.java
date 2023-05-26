package com.shelter.services.implementations;

import com.shelter.data.entities.Comment;
import com.shelter.data.entities.Role;
import com.shelter.data.entities.User;
import com.shelter.data.entities.Walk;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.data.repositories.UserRepository;
import com.shelter.data.repositories.WalkRepository;
import com.shelter.dto.*;
import com.shelter.exceptions.UserNotFoundException;
import com.shelter.exceptions.WalkNotFoundException;
import com.shelter.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<returnCommentDTO> getUserComments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Comment> comments = user.getComments();

        int totalComments = comments.size();
        int startIndex = Math.max(totalComments - 5, 0); // Starting index to retrieve comments

        List<Comment> lastFiveComments = comments.subList(startIndex, totalComments);

        List<returnCommentDTO> returnComments = new ArrayList<>();
        for (Comment comment : lastFiveComments) {
            Long walkId = comment.getWalkId();
            Walk walk = walkRepository.findById(walkId)
                    .orElseThrow(() -> new WalkNotFoundException("Walk not found"));

            returnCommentDTO returnComment = modelMapper.map(comment, returnCommentDTO.class);
            returnComment.setWalk(modelMapper.map(walk, returnWalkCommentDTO.class));
            returnComments.add(returnComment);
        }

        return returnComments;
    }

    @Override
    public HistoryAndCommentsDTO getUserHistoryAndComments(Long userId) {

        List<returnWalkDTO> walks = getHistoryByUserIdAndDate(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Comment> comments = user.getComments();

        HistoryAndCommentsDTO dto = new HistoryAndCommentsDTO(walks, null);

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
