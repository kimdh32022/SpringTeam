package com.busanit501.bootproject.service;

import com.busanit501.bootproject.dto.ChatingRoomDTO;
import com.busanit501.bootproject.dto.ChatRoomParticipantsDTO;

import java.util.List;

public interface ChatingRoomService {
    //매칭룸생성()
    long addChatingRoom(ChatingRoomDTO matchingRoomDTO, ChatRoomParticipantsDTO roomParticipantsDTO);
    //매칭룸업데이트
    void updateChatingRoom(ChatingRoomDTO matchingRoomDTO);
    void exitChatingRoom(ChatingRoomDTO matchingRoomDTO);
    void inviteChatingRoom(ChatingRoomDTO matchingRoomDTO,ChatRoomParticipantsDTO roomParticipantsDTO);
    //매칭룸삭세
    void deleteChatingRoom(long roomId);
    void deleteRoomParticipants(long roomId,long userId);
    //매칭룸전체조회
    List<ChatingRoomDTO> searchAllChatingRoom(String keyword, long userId);
}
