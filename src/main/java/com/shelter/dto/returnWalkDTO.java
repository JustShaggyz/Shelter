package com.shelter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class returnWalkDTO {
    private Long id;

    private returnUserDTO user;

    private returnDetailedAnimalDTO animal;

    private LocalDate date;

    private boolean isFinished;
}
