package com.shelter.services.implementations;

import com.shelter.data.entities.Role;
import com.shelter.data.entities.User;
import com.shelter.data.repositories.UserRepository;
import com.shelter.dto.returnUserDTO;
import com.shelter.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImplementation userService;
    @Mock
    private ModelMapper modelMapper;

    private User user1;
    private User user2;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = User.builder()
                .email("peter@gmail.com")
                .firstName("Peter")
                .lastName("Peterson")
                .build();
        user1.setId(1);

        user2 = User.builder()
                .email("james@example.com")
                .firstName("James")
                .lastName("Jameson")
                .build();
        user2.setId(2);

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
    }

    @Test
    void testGetAllUsers() {

        when(userRepository.findAll()).thenReturn(userList);

        List<returnUserDTO> result = userService.getAllUsers();

        assertEquals(userList.size(), result.size());
    }


    @Test
    void testGetVolunteers() {
        when(userRepository.findUsersByRole(Role.USER)).thenReturn(userList);

        List<returnUserDTO> result = userService.getVolunteers();

        assertEquals(userList.size(), result.size());
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserHistory() {
    }

    @Test
    void getUserComments() {
    }
}
