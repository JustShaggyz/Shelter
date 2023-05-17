package com.shelter.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "animal")
public class Animal extends BaseEntity{


    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "age", nullable = false)
    private int age;

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
