package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude ="imageSet")
public class Comments extends BaseEntity {

    @Id // PK, 기본키,
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long postId;

    private Long userId;

    private String content;

}
