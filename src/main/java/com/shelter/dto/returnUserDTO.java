package com.shelter.dto;

import com.shelter.data.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class returnUserDTO {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private Set<Role> authorities;
}
