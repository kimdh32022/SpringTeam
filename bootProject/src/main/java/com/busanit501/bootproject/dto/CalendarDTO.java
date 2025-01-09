package com.busanit501.bootproject.dto;

import com.busanit501.bootproject.domain.ScheduleStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    //    private Long matchingId;
    private String schedulename; // 일정명

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate walkDate;  // 산책 날짜
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime walkTime;  // 산책 시간

    private String walkPlace;
    private ScheduleStatus status;// 산책 상태

    private Boolean matching;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalTime schedulStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalTime schedulEnd;

    private String walkDateIso; // UTC로 변환된 날짜 (ISO 8601 포맷)

}
