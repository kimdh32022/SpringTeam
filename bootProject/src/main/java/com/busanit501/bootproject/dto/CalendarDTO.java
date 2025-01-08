package com.busanit501.bootproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDTO {

    private Long scheduleId;  // 일정 ID
    private Long userId;      // 사용자 ID
    private String schedulename; // 일정명
    private LocalDate walkDate;  // 산책 날짜
    private LocalTime walkTime;  // 산책 시간
    private String status;       // 산책 상태
    private LocalDateTime createdAt;  // 일정 생성 시간
    private LocalDateTime updatedAt;  // 일정 수정 시간


    private String walkDateIso; // UTC로 변환된 날짜 (ISO 8601 포맷)
}
