package com.busanit501.bootproject.service;

import com.busanit501.bootproject.dto.MessageDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class MessageServiceTests {
    @Autowired
    private MessageService messageService;

    @Test
    public void testAddMessage() {
//        MessageDTO messageDTO = MessageDTO.builder()
//                .chatRoomId(31)
//                .senderId(1)
//                .content("메세지 작성 테스트1")
//                .build();
//        long messageId = messageService.addMessage(messageDTO);
//        log.info(messageId);
    }

    @Test
    public void testUpdateMessage() {
//        MessageDTO messageDTO = MessageDTO.builder()
//                .messageId(4)
//                .content("메세지 수정 테스트")
//                .build();
//        messageService.updateMessage(messageDTO);
    }

    @Test
    public void testDeleteMessage() {
        long messageId = 2;
        messageService.deleteMessage(messageId);
    }

    @Test
    public void testSearchMessageByChatRoomId() {
        String keyword = "";
        long chatRoomId = 69;
        var message = messageService.searchMessage(chatRoomId);

        message.forEach(messageDTO -> {
            log.info("messageId: " + messageDTO.getMessageId());
            log.info("chatRoomId: " + messageDTO.getChatRoomId());
            log.info("senderId: " + messageDTO.getSenderId());
            log.info("content: " + messageDTO.getContent());
            log.info("sentAt: " + messageDTO.getSentAt());
            log.info("isRead: " + messageDTO.isRead());
            log.info("SenderName: " + messageDTO.getSenderName());
            log.info("-------------------------------------------");
        });
    }
}
