package com.shelter.dto;

import com.shelter.data.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class returnCurrentUserDTO {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Role role;
}
