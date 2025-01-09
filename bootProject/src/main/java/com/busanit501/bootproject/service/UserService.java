package com.busanit501.bootproject.service;

import com.busanit501.bootproject.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> searchInviteUser(String keyword, long roomId);
}
