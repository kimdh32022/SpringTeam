package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude ="imageSet")
public class Reviews extends BaseEntity {

    @Id // PK, 기본키,
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Long reviewerId;

    private Long reportedId;

    private String reason;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING,
        REWARDED,
        RESOLVED,
    }
}
