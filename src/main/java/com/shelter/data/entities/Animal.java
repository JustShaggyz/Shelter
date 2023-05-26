package com.shelter.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "animal")
public class Animal extends BaseEntity{


    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = true)
    private AnimalType type;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "description")
    private String description;

    @Column
    private boolean isAdopted;

    @Column
    private boolean isAvailable;

    @Column(name = "pictureUrl")
    private String pictureUrl;

    public Animal() {
        this.isAdopted = false;
        this.isAvailable = true;
    }
}
