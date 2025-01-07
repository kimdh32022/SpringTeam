package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.MatchingRoom;
import com.busanit501.bootproject.domain.Message;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.MessageDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    @Transactional
    public void searchMessagesByRoomId() {
        List<MessageDTO> messageList = messageRepository.searchMessageByMatchingRoomId(31);
        log.info(messageList);
    }

    @Test
    public void insertMessage() {
//        Message message = Message.builder()
//                .chatRoom(MatchingRoom.builder().roomId(31).build())
//                .sender(User.builder().userId(2).build())
//                .content("메세지3")
//                .build();
//        messageRepository.save(message);
    }

    @Test
    public void updateMessage() {
//        Optional<Message> message = messageRepository.findById(1);
//        Message updateMessage = message.orElseThrow();
//        updateMessage.MessageUpdate("수정된 내용");
//        messageRepository.save(updateMessage);
    }

    @Test
    public void deleteMessage() {
//        messageRepository.deleteById(1);
    }
}
