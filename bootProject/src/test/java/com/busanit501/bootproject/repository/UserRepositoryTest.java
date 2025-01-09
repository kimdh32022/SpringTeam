package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Log4j2
public class UserRepositoryTest {
    @Autowired
    private UserRepostiory userRepostiory;

    @Test
    @Transactional
    public void sarchUserTest(){
        String keyword = "";
        long roodId = 6;
        List<User> users = userRepostiory.searchInviteUserList(keyword,roodId);
        log.info(users);
    }
}
