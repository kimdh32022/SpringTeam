package com.busanit501.bootproject.service;


import com.busanit501.bootproject.domain.MatchingRoom;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.CalendarDTO;
import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.domain.ScheduleStatus;
import com.busanit501.bootproject.dto.MatchingRoomDTO;

import java.util.List;

public interface CalendarService {
    void saveMatchingAndCalendar(MatchingRoomDTO matchingRoomDTO);
    List<CalendarDTO> getUserSchedules(Long userId);
    void updateScheduleStatus();

    void addSchedule(CalendarDTO calendarDTO);
    void updateSchedule(Long id, CalendarDTO calendarDTO);
    void deleteSchedule(Long id);



    default CalendarDTO entityToDto(Calendar calendar) {
        return CalendarDTO.builder()
                .scheduleId(calendar.getScheduleId())
                .userId(calendar.getUser().getUserId())
                .schedulename(calendar.getSchedulename())
                .walkDate(calendar.getWalkDate())
                .walkTime(calendar.getWalkTime())
                .walkPlace(calendar.getWalkPlace())
                .status(calendar.getStatus())
                .build();
    }

    // DTO to Entity
    default Calendar dtoToEntity(CalendarDTO dto) {
        return Calendar.builder()
                .user(User.builder().userId(dto.getUserId()).build())
                .schedulename(dto.getSchedulename())
                .walkDate(dto.getWalkDate())
                .walkTime(dto.getWalkTime())
                .walkPlace(dto.getWalkPlace())
                .status(dto.getStatus())
                .build();
    }


    default MatchingRoom matchinroomdtoToCalender(MatchingRoomDTO dto) {
        return MatchingRoom.builder()
                .host(User.builder().userId(dto.getHostId()).build())
                .user(User.builder().userId(dto.getUserId()).build())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .place(dto.getPlace())
                .meetingDate(dto.getMeetingDate())
                .meetingTime(dto.getMeetingTime())
                .build();
    }

}
