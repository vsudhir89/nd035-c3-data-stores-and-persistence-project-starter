package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer petOwner = customerService.getCustomerById(petDTO.getOwnerId());
        Pet petToSave = new Pet(petDTO.getName(), petDTO.getType(), petDTO.getBirthDate(),
                petDTO.getNotes());
        if (petOwner != null) {
            petToSave.setCustomer(petOwner);
        } else {
            // If its null, set the Owner to 0?
        }
        Pet savedPet = petService.savePet(petToSave);
        if (petOwner != null) {
            petOwner.addPet(savedPet);
        }

        PetDTO petToReturn = new PetDTO();
        BeanUtils.copyProperties(savedPet, petToReturn);
        petToReturn.setOwnerId(savedPet.getCustomer().getId());
        return petToReturn;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet savedPet = petService.getPetById(petId);
        PetDTO petToReturnDTO = new PetDTO();
        if (savedPet != null) {
            BeanUtils.copyProperties(savedPet, petToReturnDTO);
            petToReturnDTO.setOwnerId(savedPet.getCustomer().getId());
        }
        return petToReturnDTO;
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> allPets = petService.getAllPets();
        List<PetDTO> petsToReturn = new ArrayList<>();
        allPets.forEach(pet -> {
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petDTO.setOwnerId(pet.getCustomer().getId());
            petsToReturn.add(petDTO);
        });
        return petsToReturn;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.getCustomerById(ownerId);
        List<Pet> allPets = customer.getPets();
        List<PetDTO> petsToReturn = new ArrayList<>();
        allPets.forEach(pet -> {
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petDTO.setOwnerId(pet.getCustomer().getId());
            petsToReturn.add(petDTO);
        });
        return petsToReturn;
    }
}
