package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public Pet savePet(Pet petToSave) {
        return petRepository.save(petToSave);
    }

    public Pet getPetById(Long petId) {
        Pet petToReturn = null;
        try {
            petToReturn = petRepository.getOne(petId);
        } catch (EntityNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.ALL, "Requested entity is not found");
        }
        return petToReturn;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}
