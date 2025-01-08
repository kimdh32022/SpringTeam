package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.Pets;
import com.busanit501.bootproject.dto.PetDTO;

import java.util.List;

public interface PetService {
    PetDTO registerPet(PetDTO petDTO);
    PetDTO updatePet(Long petId, PetDTO petDTO);
    void deletePet(Long petId);
    PetDTO getPet(Long petId);
    List<PetDTO> getAllPets();
    List<Pets> getPetsByUserId(Long userId);
}
