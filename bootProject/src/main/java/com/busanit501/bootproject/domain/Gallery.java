package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Gallery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long galleryId; // 갤러리 ID

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users users;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaType mediaType; // 미디어 타입 (ENUM)

    @Column(length = 255, nullable = false)
    private String mediaUrl; // 미디어 URL

    @Column(nullable = false)
    private LocalDateTime uploadedAt; // 업로드 시간

    @Column
    private String galleryText; // 게시글 텍스트

    public enum MediaType {
        PHOTO,
        VIDEO
    }
}
