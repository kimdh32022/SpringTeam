package com.busanit501.bootproject.dto;

import com.busanit501.bootproject.domain.RoomParticipantsStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomParticipantsDTO {
    private long roomParticipantsId;
    private long chatRoomId;
    private long senderId;
    private RoomParticipantsStatus status;
    private Timestamp createdAt;
}
