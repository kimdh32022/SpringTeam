package com.busanit501.bootproject.CalendarRepository;

import com.busanit501.bootproject.domain.Gender;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Log4j2
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // 데이터 초기화 (매번 테스트 전에 실행됨)
    }

    @Test
    public void testInsertUser() {
        User user = User.builder()
                .email("testuser7@testemail.com") // 겹치면 안됨
                .name("testuser7")
                .password("234")
                .age(29)
                .gender(Gender.FEMALE)
                .address("부산시")
                .profilePicture("http://example.com/profile.jpg")
                .phoneNumber("010-1234-5678")
                .isVerified(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 사용자 저장
        User savedUser = userRepository.save(user);
        if(savedUser.getUserId() == null) {
            throw new IllegalStateException("user id should not be null");
        }
        log.info("user sabed with id:" +savedUser.getUserId());
    }
}
