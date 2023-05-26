package com.shelter.controllers;

import com.shelter.config.auth.AuthenticationFacade;
import com.shelter.data.entities.Role;
import com.shelter.data.entities.User;
import com.shelter.dto.returnCurrentUserDTO;
import com.shelter.dto.returnDetailedUserDTO;
import com.shelter.dto.returnUserDTO;
import com.shelter.dto.returnWalkDTO;
import com.shelter.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<returnUserDTO> mockUsers = Arrays.asList(new returnUserDTO(), new returnUserDTO());
        when(userService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<List<returnUserDTO>> response = userController.getAllUsers();

        verify(userService, times(1)).getAllUsers();

        assertSame(mockUsers, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void testGetVolunteers() {
        List<returnUserDTO> mockUsers = Arrays.asList(new returnUserDTO(), new returnUserDTO());
        when(userService.getVolunteers()).thenReturn(mockUsers);

        ResponseEntity<List<returnUserDTO>> response = userController.getVolunteers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUsers, response.getBody());
        verify(userService, times(1)).getVolunteers();
    }

    @Test
    void testGetUser() {
        Long userId = 1L;
        returnDetailedUserDTO mockUser = new returnDetailedUserDTO();
        when(userService.getUserById(userId)).thenReturn(mockUser);

        ResponseEntity<returnDetailedUserDTO> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserHistoryById() {
        Long userId = 1L;
        List<returnWalkDTO> walkHistory = new ArrayList<>();
        walkHistory.add(new returnWalkDTO());
        walkHistory.add(new returnWalkDTO());
        when(userService.getUserHistory(userId)).thenReturn(walkHistory);

        ResponseEntity<List<returnWalkDTO>> response = userController.getUserHistoryById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(walkHistory, response.getBody());
        verify(userService, times(1)).getUserHistory(userId);
    }

   /* @Test
    void testGetComments() {
        Long userId = 1L;
        List<String> comments = new ArrayList<>();
        comments.add("1");
        comments.add("2");
        when(userService.getUserComments(userId)).thenReturn(comments);

        ResponseEntity<List<String>> response = userController.getComments(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
        verify(userService, times(1)).getUserComments(userId);
    }*/

    @Test
    void testGetCurrentUser() {
        User currentUser = new User();
        when(authenticationFacade.getCurrentUser()).thenReturn(currentUser);

        ResponseEntity<returnCurrentUserDTO> response = userController.getCurrentUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationFacade, times(1)).getCurrentUser();
    }

    @Test
    void testGetHistory() {
        User currentUser = new User();
        currentUser.setId(1L);
        List<returnWalkDTO> walkHistory = new ArrayList<>();
        walkHistory.add(new returnWalkDTO());
        walkHistory.add(new returnWalkDTO());
        when(authenticationFacade.getCurrentUser()).thenReturn(currentUser);
        when(userService.getUserHistory(currentUser.getId())).thenReturn(walkHistory);

        ResponseEntity<List<returnWalkDTO>> response = userController.getHistory();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(walkHistory, response.getBody());
        verify(authenticationFacade, times(1)).getCurrentUser();
        verify(userService, times(1)).getUserHistory(currentUser.getId());
    }
}