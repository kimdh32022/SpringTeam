package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.Message;
import com.busanit501.bootproject.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    long addMessage(MessageDTO messageDTO);
    void updateMessage(MessageDTO messageDTO);
    void deleteMessage(long messageId);
    void deleteAllMessagesByUser(long userId,long roomId);
    List<MessageDTO> searchMessage(long roodId);
}
