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
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            List<Employee> employees = employeeService
                    .getEmployeesForIds(scheduleDTO.getEmployeeIds());
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

                List<Long> savedPetIds = savedSchedule.getPets().stream().map(Pet::getId)
                        .collect(Collectors.toList());
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
                transformScheduleToScheduleDTO(schedule, scheduleDTO);
                scheduleToReturn.add(scheduleDTO);
            });
        }
        return scheduleToReturn;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleToReturn = new ArrayList<>();
        // get Pet from petId
        Pet pet = petService.getPetById(petId);
        // Get schedules for pet
        List<Schedule> schedulesForPet = scheduleService.getScheduleForPet(pet);
        if (schedulesForPet != null && !schedulesForPet.isEmpty()) {
            schedulesForPet.forEach(schedule -> {
                ScheduleDTO scheduleDTO = new ScheduleDTO();
                transformScheduleToScheduleDTO(schedule, scheduleDTO);
                scheduleToReturn.add(scheduleDTO);
            });
        }
        return scheduleToReturn;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleToReturn = new ArrayList<>();
        // get Employee from id
        Employee employee = employeeService.findEmployeeById(employeeId);
        // get schedules for employee
        List<Schedule> schedulesForEmployee = scheduleService.getScheduleForEmployee(employee);
        // copy properties and return
        if (schedulesForEmployee != null && !schedulesForEmployee.isEmpty()) {
            schedulesForEmployee.forEach(schedule -> {
                ScheduleDTO scheduleDTO = new ScheduleDTO();
                transformScheduleToScheduleDTO(schedule, scheduleDTO);
                scheduleToReturn.add(scheduleDTO);
            });
        }
        return scheduleToReturn;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> schedulesToReturn = new ArrayList<>();
        // get customer for customer id
        Customer customer = customerService.getCustomerById(customerId);
        // get schedules for customer
        List<Schedule> schedulesForCustomer = scheduleService.getScheduleForCustomer(customer);
        // copy properties and return
        if (schedulesForCustomer != null && !schedulesForCustomer.isEmpty()) {
            schedulesForCustomer.forEach(schedule -> {
                ScheduleDTO scheduleDTO = new ScheduleDTO();
                transformScheduleToScheduleDTO(schedule, scheduleDTO);
                schedulesToReturn.add(scheduleDTO);
            });
        }
        return schedulesToReturn;
    }

    public void transformScheduleToScheduleDTO(Schedule scheduleSource,
            ScheduleDTO scheduleDTOTarget) {
        scheduleDTOTarget.setActivities(scheduleSource.getSkills());
        scheduleDTOTarget.setDate(scheduleSource.getDate());

        List<Long> savedPetIds = scheduleSource.getPets().stream().map(Pet::getId)
                .collect(Collectors.toList());
        List<Long> savedEmployeeIds = scheduleSource.getEmployees().stream().map(
                Employee::getId).collect(
                Collectors.toList());

        scheduleDTOTarget.setPetIds(savedPetIds);
        scheduleDTOTarget.setEmployeeIds(savedEmployeeIds);
    }
}
