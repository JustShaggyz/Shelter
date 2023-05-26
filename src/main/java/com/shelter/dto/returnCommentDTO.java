package com.shelter.dto;

import com.shelter.data.entities.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class returnCommentDTO {
    private Long id;

    private Long walkId;

    private Long adminId;

    private String message;
}
