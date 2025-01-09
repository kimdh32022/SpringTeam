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
public class ChatRoomParticipantsDTO {
    private long roomParticipantsId;
    private long chatRoomId;
    private long senderId;
    private Timestamp createdAt;
}
