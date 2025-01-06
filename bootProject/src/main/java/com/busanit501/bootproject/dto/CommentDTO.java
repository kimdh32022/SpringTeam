package com.busanit501.bootproject.dto;

import com.busanit501.bootproject.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private String userName;
    private LocalDateTime createdAt;

    // Entity를 받아 DTO로 변환하는 생성자
    public CommentDTO(Comment comment) {
        this.id = comment.getCommentId();  // getId() -> getCommentId()
        this.content = comment.getContent();
        this.userName = comment.getUser().getName();
        this.createdAt = comment.getPost().getCreated_at();  // BaseEntity에서 상속된 createdAt 사용
    }

}
