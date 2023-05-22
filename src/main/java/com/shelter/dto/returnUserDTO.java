package com.shelter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class returnUserDTO {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

}
