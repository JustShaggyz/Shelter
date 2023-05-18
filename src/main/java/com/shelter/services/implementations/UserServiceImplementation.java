package com.shelter.services.implementations;

import com.shelter.data.entities.User;
import com.shelter.data.entities.Walk;
import com.shelter.data.repositories.AnimalRepository;
import com.shelter.data.repositories.UserRepository;
import com.shelter.data.repositories.WalkRepository;
import com.shelter.dto.HistoryAndCommentsDTO;
import com.shelter.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

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

    public List<Walk> getUserHistory(Long userId) {
        return getHistoryByUserIdAndDate(userId);
    }

    @Override
    public HistoryAndCommentsDTO getUserHistoryAndComments(Long userId) {

        List<Walk> walks = getHistoryByUserIdAndDate(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        List<String> comments = user.getComments();

        HistoryAndCommentsDTO dto = new HistoryAndCommentsDTO(walks, comments);

        return dto;
    }

    private List<Walk> getHistoryByUserIdAndDate(Long userId) {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        return walkRepository.findByUserIdAndDateAfter(userId, threeMonthsAgo)
                .orElseThrow(() -> new NoSuchElementException("History not found"));
    }
}
