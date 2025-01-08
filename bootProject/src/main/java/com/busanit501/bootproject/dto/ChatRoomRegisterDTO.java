package com.busanit501.bootproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomRegisterDTO {
    private ChatingRoomDTO chatingRoomDTO;
    private ChatRoomParticipantsDTO chatRoomParticipantsDTO;
}

