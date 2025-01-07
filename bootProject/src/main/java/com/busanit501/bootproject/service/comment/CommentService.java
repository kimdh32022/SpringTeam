package com.busanit501.bootproject.service.comment;

import com.busanit501.bootproject.domain.Comment;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.domain.Users;
import com.busanit501.bootproject.dto.CommentDTO;

import java.util.List;


public interface CommentService {
    List<Comment> getCommentsByPost(Long postId);
    Comment createComment(Long postId, User user, String content);
    void deleteComment(Long commentId, User user);
}