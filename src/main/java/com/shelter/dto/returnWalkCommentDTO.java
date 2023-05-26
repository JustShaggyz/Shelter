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
public class returnWalkCommentDTO {
    private Long id;

    private returnAnimalForCommentDTO animal;

    private LocalDate date;
}
