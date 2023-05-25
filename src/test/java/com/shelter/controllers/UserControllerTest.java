package com.shelter.controllers;

import com.shelter.config.auth.AuthenticationFacade;
import com.shelter.dto.returnUserDTO;
import com.shelter.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

}