package com.busanit501.bootproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude ="imageSet")
public class Messages extends BaseEntity {

    @Id // PK, 기본키,
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private Long senderId;

    private Long receiverId;

    private String content;

    private LocalDateTime sandAt;

    private boolean read;
}
