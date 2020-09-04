package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule getScheduleForId(Long id) {
        Schedule schedule = null;
        if (id != null) {
            try {
                schedule = scheduleRepository.getOne(id);
            } catch (EntityNotFoundException e) {
                Logger.getAnonymousLogger().log(Level.ALL, "Requested entity is not found");
            }
        } else {
            throw new EntityNotFoundException();
        }
        return schedule;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForEmployee(Employee employee) {
        if (employee != null) {
            return scheduleRepository.findAllByEmployee(employee);
        }
        return null;
    }

    public List<Schedule> getScheduleForPet(Pet pet) {
        if (pet != null) {
            return scheduleRepository.findAllByPet(pet);
        }
        return null;
    }

    public List<Schedule> getScheduleForCustomer(Customer customer) {
        if (customer != null) {
            return scheduleRepository.findAllByCustomer(customer);
        }
        return null;
    }
}
