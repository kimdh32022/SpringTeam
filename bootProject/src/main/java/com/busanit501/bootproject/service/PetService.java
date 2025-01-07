package com.busanit501.bootproject.service;

import com.busanit501.bootproject.dto.PetDTO;
import com.busanit501.bootproject.dto.PetSaveDTO;

import java.util.List;

public interface PetService {
    PetDTO registerPet(PetDTO petDTO);
//    PetDTO registerPet(PetSaveDTO petSaveDTO);
    PetDTO updatePet(Long petId, PetDTO petDTO);
    void deletePet(Long petId);
    PetDTO getPet(Long petId);
    List<PetDTO> getAllPets();
}
