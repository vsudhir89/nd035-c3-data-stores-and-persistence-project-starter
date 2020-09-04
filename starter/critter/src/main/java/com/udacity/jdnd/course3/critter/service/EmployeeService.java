package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Long employeeId) {
        Employee employee = null;
        try {
            employee = employeeRepository.getOne(employeeId);
        } catch (EntityNotFoundException entityNotFoundException) {
            Logger.getAnonymousLogger().log(Level.ALL, "Requested entity is not found");
        }
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesForMatchingSkillsAndDate(Set<EmployeeSkill> skills,
            DayOfWeek dayOfWeek) {
        Long skillCount = (long) skills.size();
        Set<DayOfWeek> dayAvailabilityRequested = new HashSet<>();
        dayAvailabilityRequested.add(dayOfWeek);
        if (skillCount >= 1 && dayOfWeek != null) {
            return employeeRepository.findAllBySkillsInAndDaysAvailableInNamedQuery(skills,
                    skillCount, dayAvailabilityRequested);
        }
        return null;
    }

    public List<Employee> getEmployeesForIds(List<Long> ids) {
        List<Employee> employees = null;
        if (ids != null && !ids.isEmpty()) {
            employees = employeeRepository.findAllById(ids);
        }
        return employees;
    }

    public List<Employee> getEmployeesForSkills(Set<EmployeeSkill> skillsRequested) {
        List<Employee> employees = null;
        if (skillsRequested != null && !skillsRequested.isEmpty()) {
            employees = employeeRepository.findAllBySkillsIn(skillsRequested);
        }
        return employees;
    }
}
