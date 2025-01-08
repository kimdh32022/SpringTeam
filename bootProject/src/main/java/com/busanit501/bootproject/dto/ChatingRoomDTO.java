package com.busanit501.bootproject.dto;

import com.busanit501.bootproject.domain.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatingRoomDTO {

    private long roomId;
    private long hostId;
    private String title;
    private String description;
    private long maxParticipants;
    private long currentParticipants;
    private RoomStatus status;
    private Timestamp createdAt;
}
