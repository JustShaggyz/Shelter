package com.shelter.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnimalDTO {
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

    @NotBlank
    @Size(min = 1, max = 30)
    private String type;

    @NotBlank
    @Size(min = 1, max = 30)
    private String breed;

    @Min(0)
    private int age;

    @NotBlank
    private String pictureUrl;
}
