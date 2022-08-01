package com.udacity.critter.repository;

import com.udacity.critter.entity.Employee;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulesRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> getAllByPetsContains(Pet pet);
    List<Schedule> getAllByEmployeesContains(Employee employee);
    List<Schedule> getAllByPetsIn(List<Pet> pets);
}
