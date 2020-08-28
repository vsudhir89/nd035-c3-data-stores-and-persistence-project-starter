package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Employee extends User {

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skillSet = new HashSet<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysOfWeek = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "employee_schedule",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "schedule_id")}
    )
    private List<Schedule> schedules;

    public Employee(String name, Set<EmployeeSkill> skillSet) {
        super(name);
        this.skillSet = skillSet;
    }

    public Set<EmployeeSkill> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(Set<EmployeeSkill> skillSet) {
        this.skillSet = skillSet;
    }

    public Set<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(Set<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
