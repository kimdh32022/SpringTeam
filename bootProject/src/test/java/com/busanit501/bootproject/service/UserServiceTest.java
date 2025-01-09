package com.busanit501.bootproject.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void searchUserTest() {
        String keyword = "2";
        long roomId = 2;
        var users = userService.searchInviteUser(keyword, roomId);

        users.forEach(user -> {
                log.info("유저 아이디 :" + user.getUserId());
                log.info("유저 이름 :" + user.getName());
                log.info("-----------------------------------");
        });
    }
}
