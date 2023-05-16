package com.shelter.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "walk")
public class Walk extends BaseEntity{

    @OneToOne
    private User user;

    @OneToOne
    private Animal animal;

    @Column(name = "walk_date")
    private LocalDate date;
}
