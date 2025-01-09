package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.repository.UserRepostiory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepostiory userRepostiory;

    @Override
    public List<UserDTO> searchInviteUser(String keyword, long roomId) {
        log.info("searchInviteUser keyword: " + keyword);
        log.info("searchInviteUser roomId: " + roomId);
        List<User> users = userRepostiory.searchInviteUserList(keyword,roomId);
        log.info("users  : " + users);
        List<UserDTO> dtoList = new ArrayList<>();
        for (User user : users) {
            UserDTO dto = UserDTO.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .name(user.getName())
                    .birth(user.getBirth())
                    .gender(user.getGender())
                    .address(user.getAddress())
                    .profilePicture(user.getProfilePicture())
                    .phoneNumber(user.getPhoneNumber())
                    .rating(user.getRating())
                    .ratingCount(user.getRatingCount())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }
}
