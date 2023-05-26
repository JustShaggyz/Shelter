package com.shelter.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{


    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "walk_id")
    private Long walkId;

    @Column(name = "message")
    private String message;

}
