package com.busanit501.bootproject.CalendarRepository;


import com.busanit501.bootproject.domain.Gender;
import com.busanit501.bootproject.domain.MatchingRoom;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Log4j2
@SpringBootTest
public class MatchingroomTest {



    @Autowired
    private MatchingRoomRepository matchingroomRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void matchinguser() {
        // 사용자 1과 사용자 2를 예시로 만들어서 더미 데이터를 넣어봄
        User host = User.builder()
                .name("HostUser")
                .email("hostuser@example.com")
                .password("password")
                .age(29)
                .gender(Gender.FEMALE)
                .address("부산시")
                .profilePicture("http://example.com/profile.jpg")
                .phoneNumber("010-1234-5678")
                .isVerified(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        User user = User.builder()
                .name("RegularUser")
                .email("regularuser@example.com")
                .password("password")
                .age(29)
                .gender(Gender.FEMALE)
                .address("부산시")
                .profilePicture("http://example.com/profile.jpg")
                .phoneNumber("010-1234-5678")
                .isVerified(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // User 엔티티를 DB에 저장
        userRepository.save(host);
        userRepository.save(user);
    }

    @Test
    public void matching() {
        User testuser1 = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("Host not found"));
        User testuser2 = userRepository.findById(2L).orElseThrow(() -> new RuntimeException("Host not found"));
        MatchingRoom matching = MatchingRoom.builder()
                .host(testuser1)  // host는 User 객체
                .user(testuser2)  // user도 User 객체
                .title("모닝 산책33")
                .description("매일 33시 산책")
                .place("시민공원")
                .meetingDate(LocalDate.of(2025, 1, 7))
                .meetingTime(LocalTime.of(18, 0))
                .build();

        MatchingRoom result = matchingroomRepository.save(matching);

        log.info("데이터 확인:" + result);
    }

}
