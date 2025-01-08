package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude ="imageSet")
public class Mates extends BaseEntity {

    @Id // PK, 기본키,
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mateId;

    private Long requesterId;

    private Long receiverId;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED,
        BLOCKED,
    }

}
