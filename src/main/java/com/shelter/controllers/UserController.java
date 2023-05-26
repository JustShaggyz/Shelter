package com.shelter.controllers;

import com.shelter.config.auth.AuthenticationFacade;
import com.shelter.data.entities.User;
import com.shelter.dto.*;
import com.shelter.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<returnUserDTO>> getAllUsers() {
        List<returnUserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/volunteers")
    public ResponseEntity<List<returnUserDTO>> getVolunteers() {
        List<returnUserDTO> users = userService.getVolunteers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<returnDetailedUserDTO> getUser(@PathVariable Long userId) {
        returnDetailedUserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<returnWalkDTO>> getUserHistoryById(@PathVariable Long userId) {
        List<returnWalkDTO> walkHistory = userService.getUserHistory(userId);
        return ResponseEntity.ok(walkHistory);
    }

   /* @GetMapping("/{userId}/historyAndComments")
    public ResponseEntity<HistoryAndCommentsDTO> getHistoryAndComments(@PathVariable Long userId) {
        HistoryAndCommentsDTO dto = userService.getUserHistoryAndComments(userId);
        return ResponseEntity.ok(dto);
    }*/

    @GetMapping("/{userId}/comments")
    public ResponseEntity<List<returnCommentDTO>> getComments(@PathVariable Long userId) {
        List<returnCommentDTO> comments = userService.getUserComments(userId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/profile")
    public ResponseEntity<returnCurrentUserDTO> getCurrentUser() {
        // Get the currently authenticated user
        User currentUser = authenticationFacade.getCurrentUser();

        // Create a DTO (Data Transfer Object) to represent the user data
        returnCurrentUserDTO userDto = modelMapper.map(currentUser, returnCurrentUserDTO.class);

        // Return the user data in the response body
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/history")
    public ResponseEntity<List<returnWalkDTO>> getHistory() {
        User currentUser = authenticationFacade.getCurrentUser();
        List<returnWalkDTO> walkHistory = userService.getUserHistory(currentUser.getId());
        return ResponseEntity.ok(walkHistory);
    }
}
