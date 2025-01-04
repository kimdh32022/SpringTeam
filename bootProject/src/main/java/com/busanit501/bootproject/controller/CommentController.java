package com.busanit501.bootproject.controller;


import com.busanit501.bootproject.domain.Users;
import com.busanit501.bootproject.dto.CommentDTO;
import com.busanit501.bootproject.repository.UsersRepository;
import com.busanit501.bootproject.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UsersRepository usersRepository;

    // 댓글 작성
    @PostMapping("/create")
    public String createComment(@RequestParam Long postId,
                                @RequestParam String content,
                                @RequestParam Long userId,
                                @RequestParam(required = false) Integer page) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        commentService.createComment(postId, user, content);

        // 페이지 정보가 있을 경우 유지하여 리다이렉트
        return page != null
                ? "redirect:/posts/" + postId + "?page=" + page
                : "redirect:/posts/" + postId;
    }

    // 댓글 삭제
    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id,
                                @RequestParam Long userId,
                                @RequestParam Long postId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        commentService.deleteComment(id, user);

        return "redirect:/posts/" + postId;
    }

    // 댓글 수정
    @PostMapping("/update/{id}")
    public String updateComment(@PathVariable Long id,
                                @RequestParam String content,
                                @RequestParam Long userId,
                                @RequestParam Long postId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        commentService.updateComment(id, user, content);

        return "redirect:/posts/" + postId;
    }
}
