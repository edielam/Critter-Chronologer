package com.udacity.critter.user;

import com.udacity.critter.entity.Customer;
import com.udacity.critter.entity.Employee;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.service.CustomerService;
import com.udacity.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer myCustomer = new Customer();
        myCustomer.setName(customerDTO.getName());
        myCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        myCustomer.setNotes(customerDTO.getNotes());
        List<Long> petIds = customerDTO.getPetIds();
        return getCustomerDTO(customerService.saveCustomer(myCustomer, petIds));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerService.getAllCustomers();
        return customerList.stream().map(this::getCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        return getCustomerDTO(customerService.getCustomerByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee myEmployee = new Employee();
        myEmployee.setName(employeeDTO.getName());
        myEmployee.setSkills(employeeDTO.getSkills());
        myEmployee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return getEmployeeDTO(employeeService.saveEmployee(myEmployee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return getEmployeeDTO(employeeService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeeList = employeeService.getEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills());
        return employeeList.stream().map(this::getEmployeeDTO).collect(Collectors.toList());
    }

    private CustomerDTO getCustomerDTO(Customer myCustomer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(myCustomer.getId());
        customerDTO.setName(myCustomer.getName());
        customerDTO.setPhoneNumber(myCustomer.getPhoneNumber());
        customerDTO.setNotes(myCustomer.getNotes());
        List<Long> petIds = myCustomer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private EmployeeDTO getEmployeeDTO(Employee myEmployee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(myEmployee.getId());
        employeeDTO.setName(myEmployee.getName());
        employeeDTO.setSkills(myEmployee.getSkills());
        employeeDTO.setDaysAvailable(myEmployee.getDaysAvailable());
        return employeeDTO;
    }
}
