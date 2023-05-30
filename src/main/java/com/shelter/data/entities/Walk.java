package com.shelter.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "walk")
public class Walk extends BaseEntity{

    @OneToOne
    private User user;

    @OneToOne
    private Animal animal;

    @Column
    private LocalDate date;

    @Column
    private boolean isFinished;
}
