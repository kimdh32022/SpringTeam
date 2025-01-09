package com.busanit501.bootproject.dto;



import com.busanit501.bootproject.domain.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchingRoomDTO {

    private Long roomId;
    private Long hostId;
    private Long userId;

    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;

    @NotBlank(message = "설명은 필수 입력 항목입니다.")
    private String description;

    @NotBlank(message = "장소는 필수 입력 항목입니다.")
    private String place;

    @NotNull(message = "날짜는 필수 입력 항목입니다.")
    @FutureOrPresent(message = "날짜는 현재 또는 미래여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;

    @NotNull(message = "시간은 필수 입력 항목입니다.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime meetingTime;
}
