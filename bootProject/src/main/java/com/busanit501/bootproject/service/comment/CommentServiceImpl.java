package com.busanit501.bootproject.service.comment;

import com.busanit501.bootproject.domain.Comment;
import com.busanit501.bootproject.domain.Post;
import com.busanit501.bootproject.domain.Users;
import com.busanit501.bootproject.dto.CommentDTO;
import com.busanit501.bootproject.repository.CommentRepository;
import com.busanit501.bootproject.repository.PostRepository;
import com.busanit501.bootproject.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class CommentServiceImpl implements CommentService {

        private final CommentRepository commentRepository;
        private final PostRepository postRepository;

        // 특정 게시글의 댓글 조회
        @Override
        public List<Comment> getCommentsByPost(Long postId) {
            return commentRepository.findByPostPostId(postId);
        }

        // 댓글 작성
        @Override
        @Transactional
        public Comment createComment(Long postId, User user, String content) {
            // 게시글 조회
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
            // 댓글 생성 및 저장
            Comment comment = Comment.builder()
                    .post(post)
                    .user(user)
                    .content(content)
                    .build();
            return commentRepository.save(comment);
        }

        // 댓글 삭제
        @Override
        @Transactional
        public void deleteComment(Long commentId, User user) {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
            if (!comment.getUser().getUserId().equals(user.getUserId())) {
                throw new SecurityException("삭제 권한이 없습니다.");
            }
            commentRepository.delete(comment);
        }


    }