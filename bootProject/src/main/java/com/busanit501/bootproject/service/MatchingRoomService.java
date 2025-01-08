package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.domain.MatchingRoom;
import com.busanit501.bootproject.domain.ScheduleStatus;
import com.busanit501.bootproject.dto.CalendarDTO;
import com.busanit501.bootproject.dto.MatchingRoomDTO;

public interface MatchingRoomService {

//    MatchingRoomDTO findById(Long roomId);



    default MatchingRoom dtoToEntity(MatchingRoomDTO dto) {
        // 박스에서 꺼내서, 디비 타입(Entity) 변경.
        MatchingRoom matchingroom = MatchingRoom.builder()
                .roomId(dto.getRoomId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .place(dto.getPlace())
                .meetingDate(dto.getMeetingDate())
                .meetingTime(dto.getMeetingTime())
                .build();

        return matchingroom;
    }

    // 디비 -> 화면 , Entity -> dto 변환하기.
    // 기능: 조회, 상세보기,
    default MatchingRoomDTO entityToDto(MatchingRoom matchingroom) {
        MatchingRoomDTO dto = MatchingRoomDTO.builder()
                .roomId(matchingroom.getRoomId())
                .title(matchingroom.getTitle())
                .description(matchingroom.getDescription())
                .place(matchingroom.getPlace())
                .meetingDate(matchingroom.getMeetingDate())
                .meetingTime(matchingroom.getMeetingTime())
                .build();

        return dto;
    }
}