package com.shelter.services;

import com.shelter.dto.HistoryAndCommentsDTO;
import com.shelter.dto.returnUserDTO;
import com.shelter.dto.returnDetailedUserDTO;
import com.shelter.dto.returnWalkDTO;

import java.util.List;

public interface UserService {
    List<returnUserDTO> getAllUsers();

    List<returnUserDTO> getVolunteers();

    returnDetailedUserDTO getUserById(Long userId);

    List<returnWalkDTO> getUserHistory(Long userId);
    HistoryAndCommentsDTO getUserHistoryAndComments(Long userId);

    public List<String> getUserComments(Long userId);
}
