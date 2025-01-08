package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude ="imageSet")
public class WalkSchedules extends BaseEntity {

    @Id // PK, 기본키,
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private Long userId;

    private LocalDate walkDate;

    private LocalTime walkTime;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        SCHEDULED,
        COMPLETED,
        CANCELLED
    }
}
