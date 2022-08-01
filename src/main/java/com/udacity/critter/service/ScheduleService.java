package com.udacity.critter.service;

import com.udacity.critter.entity.Customer;
import com.udacity.critter.entity.Employee;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.entity.Schedule;
import com.udacity.critter.repository.CustomersRepository;
import com.udacity.critter.repository.EmployeesRepository;
import com.udacity.critter.repository.PetsRepository;
import com.udacity.critter.repository.SchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private SchedulesRepository schedulesRepository;
    @Autowired
    private PetsRepository petsRepository;
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private CustomersRepository customersRepository;
    public List<Schedule> getAllSchedules() {
        return schedulesRepository.findAll();
    }
    public List<Schedule> getAllSchedulesForPet(long petId) {
        Pet myPet = petsRepository.getOne(petId);
        return schedulesRepository.getAllByPetsContains(myPet);
    }

    public List<Schedule> getAllSchedulesForEmployee(long employeeId) {
        Employee myEmployee = employeesRepository.getOne(employeeId);
        return schedulesRepository.getAllByEmployeesContains(myEmployee);
    }

    public List<Schedule> getAllSchedulesForCustomer(long customerId) {
        Customer myCustomer = customersRepository.getOne(customerId);
        return  schedulesRepository.getAllByPetsIn(myCustomer.getPets());
    }

    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employeeList = employeesRepository.findAllById(employeeIds);
        List<Pet> pets = petsRepository.findAllById(petIds);
        schedule.setEmployees(employeeList);
        schedule.setPets(pets);
        return schedulesRepository.save(schedule);
    }
}
