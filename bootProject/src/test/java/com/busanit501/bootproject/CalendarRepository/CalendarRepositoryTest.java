package com.busanit501.bootproject.CalendarRepository;


import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.domain.MatchingRoom;
import com.busanit501.bootproject.domain.ScheduleStatus;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.CalendarDTO;
import com.busanit501.bootproject.repository.CalendarRepository;
import com.busanit501.bootproject.repository.MatchingRoomRepository;
import com.busanit501.bootproject.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@DataJpaTest
//@Transactional
@Log4j2
@SpringBootTest
public class CalendarRepositoryTest {

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private MatchingRoomRepository matchingRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddCalendarEvent() {

        User testUser = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("user 오류"));
        MatchingRoom matching = matchingRoomRepository.findById(7L).orElseThrow(() -> new RuntimeException("matchingroom 오류"));

        // 일정 추가
        Calendar calendar = calendarRepository.save(
                Calendar.builder()
                        .user(testUser)
                        .schedulename(matching.getTitle())
                        .walkDate(matching.getMeetingDate())
                        .walkTime(matching.getMeetingTime())
                        .walkPlace(matching.getPlace())
                        .status(ScheduleStatus.SCHEDULED)
                        .matching(true)
                        .build()
        );

        // 일정 저장
        Calendar savedCalendar = calendarRepository.save(calendar);

    }


//    @Test
//    public void test() {
//        CalendarDTO calendarDTO = new CalendarDTO();
//    log.info(CalendarDTO.get);
//    }

}
