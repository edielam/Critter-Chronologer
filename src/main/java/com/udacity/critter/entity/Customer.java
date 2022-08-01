package com.udacity.critter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Table
@Data
@NoArgsConstructor
@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany(targetEntity = Pet.class)
    private List<Pet> pets;

    public void insertPet(Pet pet) {
        pets.add(pet);
    }
}
