package com.udacity.critter.service;

import com.udacity.critter.entity.Employee;
import com.udacity.critter.repository.EmployeesRepository;
import com.udacity.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeesRepository employeesRepository;
    public Employee getEmployeeById(long employeeId) {
        return employeesRepository.getOne(employeeId);
    }
    public List<Employee> getEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        List<Employee> employeeList = employeesRepository
                .getAllByDaysAvailableContains(date.getDayOfWeek()).stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
        return employeeList;
    }
    public Employee saveEmployee(Employee employee) {
        return employeesRepository.save(employee);
    }
    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee myEmployee = employeesRepository.getOne(employeeId);
        myEmployee.setDaysAvailable(daysAvailable);
        employeesRepository.save(myEmployee);
    }
}
