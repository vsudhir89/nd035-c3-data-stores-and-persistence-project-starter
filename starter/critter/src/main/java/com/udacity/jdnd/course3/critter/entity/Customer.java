package com.udacity.jdnd.course3.critter.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;

@Entity
public class Customer extends User {

    private String phoneNumber;

    @Column(length = 1000)
    private String notes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "schedule_customer",
            joinColumns = {@JoinColumn(name = "customer_id")},
            inverseJoinColumns = {@JoinColumn(name = "schedule_id")}
    )
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Schedule> schedules;

    public Customer(String name) {
        super(name);
    }

    public Customer() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setCustomer(this);
    }

    public void removePet(Pet pet) {
        pets.remove(pet);
        pet.setCustomer(null);
    }
}
