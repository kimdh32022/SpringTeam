package com.busanit501.bootproject.service;


import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.domain.MatchingRoom;
import com.busanit501.bootproject.dto.MatchingRoomDTO;
import com.busanit501.bootproject.repository.CalendarRepository;
import com.busanit501.bootproject.repository.MatchingRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MatchingRoomServicImpl implements MatchingRoomService {


    private MatchingRoomRepository matchingRoomRepository;
    private CalendarRepository calendarRepository;

    @Override
    public MatchingRoomDTO findByRoomId(Long roomId) {
        // MatchingRoom을 찾을 때 Optional을 사용하여 예외 처리를 할 수 있음
        Optional<MatchingRoom> roomid = matchingRoomRepository.findById(roomId);
        MatchingRoom matchingRoom = roomid.orElseThrow();
        MatchingRoomDTO matchingRoomDTO = entityToDto(matchingRoom);

        return matchingRoomDTO;
    }
}
