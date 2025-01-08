package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.ChatingRoom;
import com.busanit501.bootproject.domain.Message;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.MessageDTO;
import com.busanit501.bootproject.repository.ChatingRoomRepository;
import com.busanit501.bootproject.repository.MessageRepository;
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
public class MessageServiceImpl implements MessageService {
    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private UserRepostiory userRepostiory;
    @Autowired
    private ChatRoomParticipantsRepository roomParticipantsRepository;
    @Autowired
    private ChatingRoomRepository matchingRoomRepository;
    private final ModelMapper modelMapper;

    @Override
    public long addMessage(MessageDTO messageDTO) {
        Message message = modelMapper.map(messageDTO, Message.class);

        User sender = userRepostiory.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid senderId: " + messageDTO.getSenderId()));
        ChatingRoom chatRoom = matchingRoomRepository.findById(messageDTO.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid senderId: " + messageDTO.getChatRoomId()));
        message.setSender(sender);
        message.setChatRoom(chatRoom);

        Message savedMessage = messageRepository.save(message);
        return savedMessage.getMessageId();
    }

    @Override
    public void updateMessage(MessageDTO messageDTO) {
        Optional<Message> message = messageRepository.findById(messageDTO.getMessageId());
        Message savedMessage = message.orElseThrow();
        savedMessage.MessageUpdate(messageDTO.getContent());
        messageRepository.save(savedMessage);
    }

    @Override
    public void deleteMessage(long messageId) {
        messageRepository.deleteById(messageId);
    }

    @Override
    public void deleteAllMessagesByUser(long userId, long roomId) {
        messageRepository.deleteAllMessagesByUserId(userId, roomId);
    }

    @Override
    public List<MessageDTO> searchMessage(long roodId) {
        List<MessageDTO> messages = messageRepository.searchMessageByMatchingRoomId(roodId);

        List<MessageDTO> dtoList = new ArrayList<>();
        for(MessageDTO message : messages) {
            MessageDTO dto = MessageDTO.builder()
                    .messageId(message.getMessageId())
                    .chatRoomId(message.getChatRoomId())
                    .senderId(message.getSenderId())
                    .content(message.getContent())
                    .sentAt(message.getSentAt())
                    .isRead(message.isRead())
                    .senderName(message.getSenderName())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }
}
