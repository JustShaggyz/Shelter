package com.shelter.services;

import com.shelter.data.entities.User;
import com.shelter.data.entities.Walk;
import com.shelter.dto.HistoryAndCommentsDTO;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    List<Walk> getUserHistory(Long userId);
    HistoryAndCommentsDTO getUserHistoryAndComments(Long userId);
}
