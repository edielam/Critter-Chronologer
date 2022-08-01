package com.udacity.critter.service;

import com.udacity.critter.entity.Customer;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.repository.CustomersRepository;
import com.udacity.critter.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetsRepository petsRepository;
    @Autowired
    private CustomersRepository customersRepository;
    public List<Pet> getAllPets() {
        return petsRepository.findAll();
    }
    public List<Pet> getPetsByCustomerId(long customerId) {
        return petsRepository.getAllByCustomerId(customerId);
    }
    public Pet getPetById(long petId) {
        return petsRepository.getOne(petId);
    }
    public Pet savePet(Pet pet, long ownerId) {
        Customer myCustomer = customersRepository.getOne(ownerId);
        pet.setCustomer(myCustomer);
        pet = petsRepository.save(pet);
        myCustomer.insertPet(pet);
        customersRepository.save(myCustomer);
        return pet;
    }
}
