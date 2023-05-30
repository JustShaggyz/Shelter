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

    //Get all users and map to dto
    @Override
    public List<returnUserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, returnUserDTO.class))
                .collect(Collectors.toList());
    }

    //Get all volunteers and map to dto
    @Override
    public List<returnUserDTO> getVolunteers() {
        return userRepository.findUsersByRole(Role.USER)
                .stream()
                .map(user -> modelMapper.map(user, returnUserDTO.class))
                .collect(Collectors.toList());
    }


    //Get user by id and map to dto
    @Override
    public returnDetailedUserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return modelMapper.map(user, returnDetailedUserDTO.class);
    }


    //Get user history
    public List<returnWalkDTO> getUserHistory(Long userId) {
        return getHistoryByUserIdAndDate(userId);
    }

    //Get user comments
    public List<returnCommentDTO> getUserComments(Long userId) {
        //Get comments by user id
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Comment> comments = user.getComments();

        //Take last 5 comments
        int totalComments = comments.size();
        int startIndex = Math.max(totalComments - 5, 0);
        List<Comment> lastFiveComments = comments.subList(startIndex, totalComments);
        List<returnCommentDTO> returnComments = new ArrayList<>();

        //Set additional information in comment about walk and employee related to comment
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

    //Get user history for the last 3 months
    private List<returnWalkDTO> getHistoryByUserIdAndDate(Long userId) {
        //Get date 3 months ago
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        //Get history of walks by user id for the last 3 months
        return walkRepository.findByUserIdAndDateAfter(userId, threeMonthsAgo)
                .orElseThrow(() -> new NoSuchElementException("History not found"))
                .stream()
                .map(walk -> modelMapper.map(walk, returnWalkDTO.class))
                .collect(Collectors.toList());
    }
}
