package com.udacity.critter.pet;

import com.udacity.critter.entity.Pet;
import com.udacity.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet myPet = new Pet();
        myPet.setType(petDTO.getType());
        myPet.setName(petDTO.getName());
        myPet.setBirthDate(petDTO.getBirthDate());
        myPet.setNotes(petDTO.getNotes());
        return getPetDTO(petService.savePet(myPet, petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return getPetDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> petList = petService.getAllPets();
        return petList.stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> petList = petService.getPetsByCustomerId(ownerId);
        return petList.stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    private PetDTO getPetDTO(Pet myPet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(myPet.getId());
        petDTO.setName(myPet.getName());
        petDTO.setType(myPet.getType());
        petDTO.setOwnerId(myPet.getCustomer().getId());
        petDTO.setBirthDate(myPet.getBirthDate());
        petDTO.setNotes(myPet.getNotes());
        return petDTO;
    }
}
