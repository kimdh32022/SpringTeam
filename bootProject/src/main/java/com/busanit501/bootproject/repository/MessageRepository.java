package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Message;
import com.busanit501.bootproject.dto.MessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT new com.busanit501.bootproject.dto.MessageDTO(a.messageId, a.chatRoom.roomId, a.sender.userId, a.content, a.sentAt, a.isRead, u.name) " +
            "FROM Message a " +
            "JOIN a.sender u " +
            "WHERE a.chatRoom.roomId = :roomId " +
            "ORDER BY a.messageId ASC ")
    List<MessageDTO> searchMessageByMatchingRoomId(long roomId);

    @Transactional
    @Modifying
    @Query(value = "DELETE m " +
            "FROM message m " +
            "WHERE m.chat_room_id = :roomId " +
            "AND m.sender_id = :userId " ,nativeQuery = true)
    void deleteAllMessagesByUserId(long userId,long roomId);
}
