package com.busanit501.bootproject.service.Impl;

import com.busanit501.bootproject.domain.Users;
import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.repository.UserRepository;
import com.busanit501.bootproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO.getProfilePicture() != null && !userDTO.getProfilePicture().isEmpty()) {
            String fileName = userDTO.getProfilePictureFile().getOriginalFilename();
            String uploadDir = "C:/upload/img/";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
            }
            try {
                Path path = Paths.get(uploadDir + fileName);
                userDTO.getProfilePictureFile().transferTo(path);
                userDTO.setProfilePicture("/img" + fileName);
            }catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        } else {
            userDTO.setProfilePicture("default.jpg");
        }
        Users users = modelMapper.map(userDTO, Users.class);
        Users savedUsers = userRepository.save(users);
        return modelMapper.map(savedUsers, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Users not found"));
        return modelMapper.map(users, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        Users existingUsers = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Users not found"));
        modelMapper.map(userDTO, existingUsers);
        Users updatedUsers = userRepository.save(existingUsers);
        return modelMapper.map(updatedUsers, UserDTO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean checkEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Users users = userRepository.findByEmail(email);
        return users != null ? modelMapper.map(users, UserDTO.class) : null;
    }
}
