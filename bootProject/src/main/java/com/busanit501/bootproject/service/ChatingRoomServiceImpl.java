package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.*;
import com.busanit501.bootproject.dto.ChatingRoomDTO;
import com.busanit501.bootproject.dto.ChatRoomParticipantsDTO;
import com.busanit501.bootproject.repository.ChatingRoomRepository;
import com.busanit501.bootproject.repository.ChatRoomParticipantsRepository;
import com.busanit501.bootproject.repository.UserRepostiory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ChatingRoomServiceImpl implements ChatingRoomService {
    @Autowired
    private ChatingRoomRepository chatingRoomRepository;
    @Autowired
    private UserRepostiory userRepostiory;
    @Autowired
    private ChatRoomParticipantsRepository chatRoomParticipantsRepository;
    private final ModelMapper modelMapper;

    @Override
    public long addChatingRoom(ChatingRoomDTO chatingRoomDTO, ChatRoomParticipantsDTO chatRoomParticipantsDTO) {
        ChatingRoom chatingRoom = modelMapper.map(chatingRoomDTO, ChatingRoom.class);
        ChatRoomParticipants roomParticipants = modelMapper.map(chatRoomParticipantsDTO, ChatRoomParticipants.class);

        // hostId로 User 엔티티 조회
        User host = userRepostiory.findById(chatingRoomDTO.getHostId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid hostId: " + chatingRoomDTO.getHostId()));

        chatingRoom.setHost(host);

        // 매칭룸 저장
        ChatingRoom savedRoom = chatingRoomRepository.save(chatingRoom);

        // RoomParticipants에 host 정보 추가
        roomParticipants.setChatRoom(savedRoom); //
        roomParticipants.setSender(host); //

        // RoomParticipants 저장
        chatRoomParticipantsRepository.save(roomParticipants);

        return savedRoom.getRoomId();
    }


    @Override
    public void updateChatingRoom(ChatingRoomDTO chatingRoomDTO) {
        Optional<ChatingRoom> result = chatingRoomRepository.findById(chatingRoomDTO.getRoomId());
        ChatingRoom chatingRoom = result.orElseThrow();
        chatingRoom.ChatingRoomUpdate(chatingRoomDTO.getTitle(),
                chatingRoomDTO.getDescription(),
                chatingRoomDTO.getMaxParticipants(),
                chatingRoomDTO.getStatus());
        chatingRoomRepository.save(chatingRoom);
    }
    @Override
    public void exitChatingRoom(ChatingRoomDTO chatingRoomDTO){
        Optional<ChatingRoom> result = chatingRoomRepository.findById(chatingRoomDTO.getRoomId());
        ChatingRoom chatingRoom = result.orElseThrow();
        chatingRoom.exitRoom(chatingRoomDTO.getCurrentParticipants());
        chatingRoomRepository.save(chatingRoom);
    }

    @Override
    public void inviteChatingRoom(ChatingRoomDTO chatingRoomDTO, ChatRoomParticipantsDTO chatRoomParticipantsDTO) {
        // 채팅방 정보를 가져옴
        Optional<ChatingRoom> result = chatingRoomRepository.findById(chatingRoomDTO.getRoomId());
        ChatingRoom chatingRoom = result.orElseThrow();

        // 현재 참가자 수를 업데이트: 현재 참가자 수에 초대된 사람 수만큼 증가
        chatingRoom.inviteRoom(chatingRoom.getCurrentParticipants()); // inviteRoom 메소드를 호출하여 참가자 수 증가

        // 채팅방 정보 저장
        chatingRoomRepository.save(chatingRoom);

        // 초대된 유저를 참가자로 추가
        ChatRoomParticipants participant = ChatRoomParticipants.builder()
                .chatRoom(chatingRoom)  // 초대된 채팅방 정보 설정
                .sender(User.builder()
                        .userId(chatRoomParticipantsDTO.getSenderId()) // 유저 ID로 엔티티 빌드
                        .build())
                .build();

        // 참가자 정보 저장
        chatRoomParticipantsRepository.save(participant);
    }



    @Override
    public void deleteChatingRoom(long roomId) {
        chatingRoomRepository.deleteById(roomId);
    }

    @Override
    public void deleteRoomParticipants(long roomId, long userId) {
        chatRoomParticipantsRepository.deleteByRoomIdAndUserId(roomId, userId);
    }

    @Override
    public List<ChatingRoomDTO> searchAllChatingRoom(String keyword, long userId) {
        // 키워드로 매칭룸 검색
        List<ChatingRoom> chatingRooms = chatingRoomRepository.searchAllChatingRoom(keyword,userId);

        // 검색 결과를 DTO로 변환
        List<ChatingRoomDTO> dtoList = new ArrayList<>();
        for (ChatingRoom chatingRoom : chatingRooms) {
            ChatingRoomDTO dto = ChatingRoomDTO.builder()
                    .roomId(chatingRoom.getRoomId())
                    .hostId(chatingRoom.getHost().getUserId()) // User 엔티티에서 hostId 추출
                    .title(chatingRoom.getTitle())
                    .description(chatingRoom.getDescription())
                    .maxParticipants(chatingRoom.getMaxParticipants())
                    .currentParticipants(chatingRoom.getCurrentParticipants())
                    .status(chatingRoom.getStatus())
                    .createdAt(chatingRoom.getCreatedAt())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }
}
