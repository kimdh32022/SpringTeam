package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.Message;
import com.busanit501.bootproject.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    int addMessage(MessageDTO messageDTO);
    void updateMessage(MessageDTO messageDTO);
    void deleteMessage(int messageId);
    List<MessageDTO> searchMessage(int roodId);
}
