package com.busanit501.bootproject.service;


import com.busanit501.bootproject.dto.CalendarDTO;
import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.domain.ScheduleStatus;

import java.util.List;

public interface CalendarService {
    Long register(CalendarDTO calendarDTO);
    CalendarDTO readOne(Long id);
    void update(CalendarDTO calendarDTO);
    void delete(Long id);
    List<CalendarDTO> getAllCalendars();
    List<CalendarDTO> getUserCalendars(Long userId);

    void updateScheduledEvents();


    default Calendar dtoToEntity(CalendarDTO dto) {
        // 박스에서 꺼내서, 디비 타입(Entity) 변경.
        Calendar calendar = Calendar.builder()
                .scheduleId(dto.getScheduleId())//일정 id
                .schedulename(dto.getSchedulename())
                .walkDate(dto.getWalkDate()) // 산책 날짜
                .walkTime(dto.getWalkTime())//산책 시간
                .status(ScheduleStatus.valueOf(dto.getStatus()))// 산책 일정
                .build();

        return calendar;
    }

    // 디비 -> 화면 , Entity -> dto 변환하기.
    // 기능: 조회, 상세보기,
    default CalendarDTO entityToDto(Calendar calendar) {
        CalendarDTO calendarDTO = CalendarDTO.builder()
                .scheduleId(calendar.getScheduleId())
                .schedulename(calendar.getSchedulename())
                .walkDate(calendar.getWalkDate())
                .walkTime(calendar.getWalkTime())
                .status(String.valueOf(calendar.getStatus()))
//                .createdAt(calendar.getCreatedAt())
//                .updatedAt(calendar.getUpdatedAt())
                .build();

        return calendarDTO;
    }

}
