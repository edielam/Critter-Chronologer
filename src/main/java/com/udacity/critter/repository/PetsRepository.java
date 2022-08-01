package com.udacity.critter.repository;

import com.udacity.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetsRepository extends JpaRepository<Pet, Long> {
    List<Pet> getAllByCustomerId(Long customerId);

}
