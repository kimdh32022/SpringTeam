package com.busanit501.bootproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
    private int messageId;
    private int chatRoomId;
    private int senderId;
    private String content;
    private Timestamp sentAt;
    private boolean isRead;

    private String senderName;
}
