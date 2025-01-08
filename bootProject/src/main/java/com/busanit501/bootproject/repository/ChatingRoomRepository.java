package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.ChatingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatingRoomRepository extends JpaRepository<ChatingRoom, Long> {
    //매칭방 조회(로그인 회원이 포함된 매칭방)
    @Query(value = "SELECT DISTINCT a.* " +
            "FROM chating_room a LEFT JOIN message b ON a.room_id = b.chat_room_id " +
            "LEFT JOIN chat_room_participants rp ON a.room_id = rp.chat_room_id " +
            "WHERE a.title like concat('%', :keyword, '%') " +
            "AND rp.sender_id = :userId " +
            "ORDER BY IFNULL(b.sent_at, a.created_at) DESC",
            nativeQuery = true)
    List<ChatingRoom> searchAllChatingRoom(String keyword, long userId);

    //열린 매칭방만 조회(최근 채팅 정렬)
//    @Query(value = "SELECT DISTINCT a.* " +
//            "FROM matching_room a LEFT JOIN message b ON a.room_id = b.chat_room_id  " +
//            "WHERE a.title like concat('%', :keyword, '%') " +
//            "AND a.status = 'Open' " +
//            "ORDER BY IFNULL(b.sent_at, a.created_at) DESC",
//            nativeQuery = true)
//    List<ChatingRoom> findMatchingRoomByOnOff(String keyword);
}
