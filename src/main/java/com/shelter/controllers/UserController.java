package com.shelter.controllers;

import com.shelter.data.entities.User;
import com.shelter.data.entities.Walk;
import com.shelter.dto.HistoryAndCommentsDTO;
import com.shelter.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<Walk>> getHistory(@PathVariable Long userId) {
        List<Walk> walkHistory = userService.getUserHistory(userId);
        return ResponseEntity.ok(walkHistory);
    }

    @GetMapping("{userId}/historyAndComments")
    public ResponseEntity<HistoryAndCommentsDTO> getHistoryAndComments(@PathVariable Long userId) {
        HistoryAndCommentsDTO dto = userService.getUserHistoryAndComments(userId);
        return ResponseEntity.ok(dto);
    }
}
