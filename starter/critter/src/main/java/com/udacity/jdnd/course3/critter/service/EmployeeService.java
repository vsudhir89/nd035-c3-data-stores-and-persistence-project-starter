package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;
import org.hibernate.annotations.common.util.impl.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
}
