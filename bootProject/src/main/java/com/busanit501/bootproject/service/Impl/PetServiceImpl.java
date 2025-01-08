package com.busanit501.bootproject.service.Impl;

import com.busanit501.bootproject.domain.Pets;
import com.busanit501.bootproject.domain.Users;
import com.busanit501.bootproject.dto.PetDTO;
import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.repository.PetRepository;
import com.busanit501.bootproject.repository.UserRepository;
import com.busanit501.bootproject.service.PetService;
import com.busanit501.bootproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    // 펫 등록
    @Override
    public PetDTO registerPet(PetDTO petDTO) {
        // 사용자 ID를 기반으로 UserDTO 객체 가져오기
        UserDTO userDTO = userService.getUserById(petDTO.getUserId());
        if (userDTO == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다."); // 사용자 검증
        }

        // DTO에서 Pets 엔티티로 변환
        Pets pet = modelMapper.map(petDTO, Pets.class); // PetDTO에서 Pets 엔티티로 변환

        // UserDTO에서 Users 엔티티로 변환
        Users user = modelMapper.map(userDTO, Users.class); // UserDTO에서 Users 엔티티로 변환

        // Pets 엔티티에 사용자 설정
        pet.setUsers(user);

        // 엔티티 저장
        Pets savedPet = petRepository.save(pet);

        // 저장된 엔티티를 다시 DTO로 변환
        return modelMapper.map(savedPet, PetDTO.class);
    }

    //펫 수정
    @Override
    public PetDTO updatePet(Long petId, PetDTO petDTO) {
        Pets existingPet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("펫을 찾을 수 없습니다."));

        modelMapper.map(petDTO, existingPet); // DTO의 값으로 기존 엔티티 업데이트
        Pets updatedPet = petRepository.save(existingPet); // 업데이트된 엔티티 저장
        return modelMapper.map(updatedPet, PetDTO.class); // 업데이트된 엔티티를 DTO로 변환
    }

    //펫 삭제
    @Override
    public void deletePet(Long petId) {
        petRepository.deleteById(petId); // 펫 삭제
    }

    // 펫 조회
    @Override
    public PetDTO getPet(Long petId) {
        Pets pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("펫을 찾을 수 없습니다."));
        return modelMapper.map(pet, PetDTO.class); // 엔티티를 DTO로 변환
    }

    // 모든 펫 조회
    @Override
    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(pet -> modelMapper.map(pet, PetDTO.class)) // 엔티티를 DTO로 변환
                .collect(Collectors.toList()); // 리스트로 변환
    }

    // 사용자 ID로 펫 조회
    @Override
    public List<Pets> getPetsByUserId(Long userId) {
        // userId로 Users 객체를 가져오기
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // Users 객체를 기준으로 Pets 리스트 조회
        return petRepository.findByUsers(user);
    }
}
