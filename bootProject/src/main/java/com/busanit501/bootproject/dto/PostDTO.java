package com.busanit501.bootproject.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long postId;
    private Long userId;
    private String userName;
    private UserDTO user;
    private String category;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private List<CommentDTO> comments;
}