package com.shelter.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 30)
    private String type;

    @Size(max = 30)
    private String breed;

    @NotNull
    @Max(value = 100)
    private Integer age;

    @Size(max = 300)
    private String description;

    private MultipartFile picture;
}
