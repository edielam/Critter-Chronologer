package com.udacity.critter.schedule;

import com.udacity.critter.entity.Employee;
import com.udacity.critter.entity.Pet;
import com.udacity.critter.entity.Schedule;
import com.udacity.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService schedulesService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule mySchedule = new Schedule();
        mySchedule.setDate(scheduleDTO.getDate());
        mySchedule.setActivities(scheduleDTO.getActivities());
        return getScheduleDTO(schedulesService.saveSchedule(mySchedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = schedulesService.getAllSchedules();
        return scheduleList.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = schedulesService.getAllSchedulesForPet(petId);
        return scheduleList.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = schedulesService.getAllSchedulesForEmployee(employeeId);
        return scheduleList.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = schedulesService.getAllSchedulesForCustomer(customerId);
        return scheduleList.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    private ScheduleDTO getScheduleDTO(Schedule mySchedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(mySchedule.getId());
        scheduleDTO.setEmployeeIds(mySchedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(mySchedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setDate(mySchedule.getDate());
        scheduleDTO.setActivities(mySchedule.getActivities());
        return scheduleDTO;
    }
}
