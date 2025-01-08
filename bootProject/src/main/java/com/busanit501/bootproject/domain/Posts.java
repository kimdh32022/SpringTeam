package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude ="imageSet")
public class Posts extends BaseEntity {

    @Id // PK, 기본키,
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    private String content;

    public enum Category {
        PENDING,
        REWARDED,
        RESOLVED,
    }

}
