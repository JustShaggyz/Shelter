package com.shelter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class returnDetailedAnimalDTO {
    private Long id;

    private String name;

    private returnAnimalType type;

    private String breed;

    private int age;

    private String description;

    private String pictureUrl;

    private boolean isAvailable;
}
