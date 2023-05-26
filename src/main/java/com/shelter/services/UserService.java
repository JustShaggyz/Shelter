package com.shelter.services;

import com.shelter.dto.*;

import java.util.List;

public interface UserService {
    List<returnUserDTO> getAllUsers();

    List<returnUserDTO> getVolunteers();

    returnDetailedUserDTO getUserById(Long userId);

    List<returnWalkDTO> getUserHistory(Long userId);
    HistoryAndCommentsDTO getUserHistoryAndComments(Long userId);

    public List<returnCommentDTO> getUserComments(Long userId);
}
