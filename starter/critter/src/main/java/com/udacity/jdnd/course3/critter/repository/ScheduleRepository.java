package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where :employee member of s.employees")
    List<Schedule> findAllByEmployee(Employee employee);

    @Query("select s from Schedule s where :pet member of s.pets")
    List<Schedule> findAllByPet(Pet pet);

    @Query("select s from Schedule s where :customer member of s.customers")
    List<Schedule> findAllByCustomer(Customer customer);
}
