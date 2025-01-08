package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.ChatingRoom;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Log4j2
public class MatchingRoomRepositoryTest {

    @Autowired
    private ChatingRoomRepository matchingRoomRepository;

    //매칭방 조회
    @Test
    @Transactional
    public void searchAllMatchingRoomTest() {
        List<ChatingRoom> matchingRooms = matchingRoomRepository.searchAllMatchingRoom("",1);
        log.info(matchingRooms);
    }

    //매칭방 조회(열린방만)
    @Test
    @Transactional
    public void findMatchingRoomByOnOffTest() {
        List<ChatingRoom> matchingRooms = matchingRoomRepository.findMatchingRoomByOnOff("");
        log.info(matchingRooms);
    }

    //매칭방 추가
    @Test
    public void insertMatchingRoomTest() {
//        MatchingRoom matchingRoom = MatchingRoom.builder()
//                .host(User.builder().userId(1).build())
//                .title("매칭룸3")
//                .description("내용3")
//                .maxParticipants(2)
//                .currentParticipants(2)
//                .status(RoomStatus.Open)
//                .build();
//        matchingRoomRepository.save(matchingRoom);
    }

    @Test
    public void updateMatchingRoomTest() {
//        Optional<MatchingRoom> matchingRoom = matchingRoomRepository.findById(4);
//        MatchingRoom UpdateMatchingRoom = matchingRoom.orElseThrow();
//        UpdateMatchingRoom.MatchingRoomUpdate("방이름 수정","방설명 수정",3,RoomStatus.Open);
//        matchingRoomRepository.save(UpdateMatchingRoom);
    }

    @Test
    public void deleteMatchingRoomTest() {
//        matchingRoomRepository.deleteById(4);
    }
}
