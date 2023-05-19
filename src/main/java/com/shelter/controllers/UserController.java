package com.shelter.controllers;

import com.shelter.dto.HistoryAndCommentsDTO;
import com.shelter.dto.returnUserDTO;
import com.shelter.dto.returnDetailedUserDTO;
import com.shelter.dto.returnWalkDTO;
import com.shelter.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<returnUserDTO>> getAllUsers() {
        List<returnUserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<returnDetailedUserDTO> getUser(@PathVariable Long userId) {
        returnDetailedUserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<returnWalkDTO>> getHistory(@PathVariable Long userId) {
        List<returnWalkDTO> walkHistory = userService.getUserHistory(userId);
        return ResponseEntity.ok(walkHistory);
    }

    @GetMapping("/{userId}/historyAndComments")
    public ResponseEntity<HistoryAndCommentsDTO> getHistoryAndComments(@PathVariable Long userId) {
        HistoryAndCommentsDTO dto = userService.getUserHistoryAndComments(userId);
        return ResponseEntity.ok(dto);
    }
}
