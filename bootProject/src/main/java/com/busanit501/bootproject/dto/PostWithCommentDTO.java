package com.busanit501.bootproject.dto;

import com.busanit501.bootproject.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostWithCommentDTO {
    private Long postId;
    private String title;
    private String content;
    private Category category;
    private String userName;
    private String userAddress;
    private LocalDateTime createdAt;
    private Long commentCount;
}
