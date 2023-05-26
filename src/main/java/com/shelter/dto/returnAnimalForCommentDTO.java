package com.shelter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class returnAnimalForCommentDTO {
    private Long id;

    private String name;

    private String pictureUrl;
}
