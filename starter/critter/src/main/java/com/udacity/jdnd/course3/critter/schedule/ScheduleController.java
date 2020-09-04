package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO scheduleToReturn = null;
        if (scheduleDTO != null) {
            // Convert to Schedule object
            Schedule scheduleToSave = new Schedule(scheduleDTO.getDate());
            // get employees that match the given ids
            List<Employee> employees = employeeService.getEmployeesForIds(scheduleDTO.getEmployeeIds());
            // get pets that match the given ids
            List<Pet> pets = petService.getAllPetsForIds(scheduleDTO.getPetIds());
            // get customer for pet ids
            List<Customer> customers = customerService.getAllCustomersForPets(pets);

            scheduleToSave.setEmployees(employees);
            scheduleToSave.setPets(pets);
            scheduleToSave.setSkills(scheduleDTO.getActivities());
            scheduleToSave.setCustomers(customers);

            Schedule savedSchedule = scheduleService.saveSchedule(scheduleToSave);
            scheduleToReturn = new ScheduleDTO();
            if (savedSchedule != null) {
                scheduleToReturn.setActivities(savedSchedule.getSkills());
                scheduleToReturn.setDate(savedSchedule.getDate());

                List<Long> savedEmployeeIds = savedSchedule.getEmployees().stream().map(
                        Employee::getId).collect(
                        Collectors.toList());
                scheduleToReturn.setEmployeeIds(savedEmployeeIds);

                List<Long> savedPetIds = savedSchedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
                scheduleToReturn.setPetIds(savedPetIds);
            }
        }
        return scheduleToReturn;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> savedSchedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleToReturn = new ArrayList<>();
        if (savedSchedules != null && !savedSchedules.isEmpty()) {
            savedSchedules.forEach(schedule -> {
                ScheduleDTO scheduleDTO = new ScheduleDTO();
                List<Long> savedPetIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
                List<Long> savedEmployeeIds = schedule.getEmployees().stream().map(
                        Employee::getId).collect(
                        Collectors.toList());
                scheduleDTO.setPetIds(savedPetIds);
                scheduleDTO.setEmployeeIds(savedEmployeeIds);
                scheduleDTO.setDate(schedule.getDate());
                scheduleDTO.setActivities(schedule.getSkills());
                scheduleToReturn.add(scheduleDTO);
            });
        }
        return scheduleToReturn;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }
}
