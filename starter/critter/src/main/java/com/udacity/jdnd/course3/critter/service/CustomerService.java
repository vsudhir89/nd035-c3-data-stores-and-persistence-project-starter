package com.udacity.jdnd.course3.critter.service;

import com.google.common.collect.Lists;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return Lists.newArrayList(customerRepository.findAll());
    }

    public Customer getCustomerById(Long ownerId) {
        Customer customer = null;
        try {
            customer = customerRepository.getOne(ownerId);
        } catch (EntityNotFoundException entityNotFoundException) {
            Logger.getAnonymousLogger().log(Level.ALL, "Requested entity is not found");
        }
        return customer;
    }

    public Customer getCustomerByPet(Pet pet) {
        List<Customer> petOwnerList = customerRepository.findAllByPet(pet);
        Optional<Customer> optionalCustomer = petOwnerList.stream().findFirst();
        return optionalCustomer.orElse(null);
    }

    public List<Customer> getAllCustomersForPets(List<Pet> pets) {
        List<Customer> customers = null;
        if (pets != null && !pets.isEmpty()) {
            customers = customerRepository.findAllByPetsIn(pets);
        }
        return customers;
    }
}
