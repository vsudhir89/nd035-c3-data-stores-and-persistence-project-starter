package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and
 * customer controllers would be fine too, though that is not part of the required scope for this
 * class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    PetService petService;

    // ----------------------------------- Customer/Owner ------------------------------------
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customerToSave = new Customer(customerDTO.getName());
        customerToSave.setPhoneNumber(customerDTO.getPhoneNumber());
        customerToSave.setNotes(customerDTO.getNotes());

        Customer savedCustomer = customerService.saveCustomer(customerToSave);

        CustomerDTO newCustomerDTO = new CustomerDTO();
        newCustomerDTO.setId(savedCustomer.getId());
        newCustomerDTO.setName(savedCustomer.getName());
        newCustomerDTO.setNotes(savedCustomer.getNotes());
        newCustomerDTO.setPhoneNumber(savedCustomer.getPhoneNumber());
        return newCustomerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> savedCustomers = customerService.getAllCustomers();
        List<CustomerDTO> allCustomers = new ArrayList<>();
        savedCustomers.forEach(customer -> {
            CustomerDTO newCustomer = new CustomerDTO();

            List<Long> petIdsForNewCustomer = new ArrayList<>();
            customer.getPets().forEach(pet -> petIdsForNewCustomer.add(pet.getId()));
            newCustomer.setPetIds(petIdsForNewCustomer);

            BeanUtils.copyProperties(customer, newCustomer);
            allCustomers.add(newCustomer);
        });

        return allCustomers;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        CustomerDTO ownerToReturn = new CustomerDTO();
        if (pet != null) {
            Customer owner = customerService.getCustomerByPet(pet);

            List<Long> petIds = new ArrayList<>();

            owner.getPets().forEach(ownerPet -> petIds.add(ownerPet.getId()));
            ownerToReturn.setPetIds(petIds);

            BeanUtils.copyProperties(owner, ownerToReturn);
        }
        return ownerToReturn;
    }

    // ------------------------------------ Employee -------------------------------------
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
            @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

}
