package com.busanit501.bootproject.CalendarRepository;


import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.domain.Gender;
import com.busanit501.bootproject.domain.MatchingRoom;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.MatchingRoomDTO;
import com.busanit501.bootproject.repository.CalendarRepository;
import com.busanit501.bootproject.repository.MatchingRoomRepository;
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


    @Autowired
    private CalendarRepository calendarRepository;

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
        User testuser1 = userRepository.findById(8L).orElseThrow(() -> new RuntimeException("Host not found"));
        User testuser4 = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("Host not found"));
        MatchingRoom matching = MatchingRoom.builder()
                .host(testuser1)  // host는 User 객체
                .user(testuser4)  // user도 User 객체
                .title("매칭룸13 데스트")
                .description("테스트")
                .place("시민공원 산책로")
                .meetingDate(LocalDate.of(2025, 1, 20))
                .meetingTime(LocalTime.of(18, 0))
                .build();

        MatchingRoom result = matchingroomRepository.save(matching);

        log.info("데이터 확인:" + result);
    }
//
//    @Test
//    public void matchingToCalendar() {
//        // 1. 테스트 데이터 준비: 사용자 가져오기
//        User testuser1 = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("Host not found"));
//        User testuser2 = userRepository.findById(2L).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 2. MatchingRoom 생성
//        MatchingRoom matching = MatchingRoom.builder()
//                .host(testuser1)  // Host 설정
//                .user(testuser2)  // User 설정
//                .title("오후 산책")
//                .description("18시 산책")
//                .place("공원에서")
//                .meetingDate(LocalDate.of(2025, 1, 26))
//                .meetingTime(LocalTime.of(18, 0))
//                .build();
//
//        // 3. MatchingRoom 저장
//        MatchingRoom result = matchingroomRepository.save(matching);
//        log.info("매칭룸 저장 결과: " + result);
//
//        // 4. Calendar 생성 및 저장
//        Calendar calendar = result.createCalendarFromMatching(); // 캘린더 생성
//        Calendar savedCalendar = calendarRepository.save(calendar); // 캘린더 저장
//        log.info("캘린더 저장 결과: " + savedCalendar);
//    }
//
//
//


}
