package com.busanit501.bootproject.service;


import com.busanit501.bootproject.domain.MatchingRoom;
import com.busanit501.bootproject.domain.ScheduleStatus;
import com.busanit501.bootproject.dto.CalendarDTO;
import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.dto.MatchingRoomDTO;
import com.busanit501.bootproject.repository.CalendarRepository;
import com.busanit501.bootproject.repository.MatchingRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final MatchingRoomRepository matchingRoomRepository;

    @Override
    public List<CalendarDTO> getUserSchedules(Long userId) {
        List<Calendar> calendars = calendarRepository.findUserId(userId);
        return calendars.stream().map(this::entityToDto).collect(Collectors.toList());
    }


    @Override
    public void saveMatchingAndCalendar(MatchingRoomDTO matchingRoomDTO) {
        MatchingRoom matchingRoom = matchinroomdtoToCalender(matchingRoomDTO);
        matchingRoomRepository.save(matchingRoom);

        Calendar calendar = Calendar.builder()
                .user(matchingRoom.getUser())
                .schedulename(matchingRoom.getTitle())
                .walkDate(matchingRoom.getMeetingDate())
                .walkTime(matchingRoom.getMeetingTime())
                .walkPlace(matchingRoom.getPlace())
                .matching(true)  // matching된 일정이라는 뜻
                .status(ScheduleStatus.SCHEDULED)
                .build();

        calendarRepository.save(calendar);
        log.info("MatchingRoom과 Calendar 데이터 저장 완료");
    }

    @Override
    public void addSchedule(CalendarDTO calendarDTO) {
        Calendar calendar = dtoToEntity(calendarDTO);
        calendarRepository.save(calendar);
    }

    @Override
    public void updateSchedule(Long id, CalendarDTO calendarDTO) {
        // 기존 일정 찾기
        Calendar existingCalendar = calendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정을 찾을 수 없습니다."));
        Calendar updatedCalendar = dtoToEntity(calendarDTO);
        existingCalendar = updatedCalendar;
        calendarRepository.save(existingCalendar);
    }

    @Override
    public void deleteSchedule(Long id) {
        calendarRepository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "0 0 * * * *") // 매 시간마다 실행
    @Transactional
    public void updateScheduleStatus() {
        List<Calendar> schedules = calendarRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        schedules.forEach(schedule -> {
            LocalDateTime scheduleTime = LocalDateTime.of(schedule.getWalkDate(), schedule.getWalkTime());
            if (scheduleTime.isBefore(now)) {
                schedule.changeStatus(ScheduleStatus.COMPLETED);
            }
        });
        calendarRepository.saveAll(schedules);
        log.info("ScheduleStatus 업데이트 완료");
    }


}
