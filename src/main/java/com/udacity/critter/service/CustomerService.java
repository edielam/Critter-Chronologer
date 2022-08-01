package com.udacity.critter.service;

import com.udacity.critter.entity.Customer;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.repository.CustomersRepository;
import com.udacity.critter.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private PetsRepository petsRepository;
    public List<Customer> getAllCustomers() {
        return customersRepository.findAll();
    }
    public Customer getCustomerByPetId(long petId) {
        return petsRepository.getOne(petId).getCustomer();
    }
    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Pet> petList = new ArrayList<>();
        if (petIds != null && !petIds.isEmpty()) {
            petList = petIds.stream().map((petId) -> petsRepository.getOne(petId)).collect(Collectors.toList());
        }
        customer.setPets(petList);
        return customersRepository.save(customer);
    }
}
