package com.busanit501.bootproject.controller;


import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.repository.UserRepository;
import com.busanit501.bootproject.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;

    // 댓글 작성
    @PostMapping("/create")
    public String createComment(@RequestParam Long postId,
                                @RequestParam String content,
                                @RequestParam Long userId,
                                @RequestParam(required = false) Integer page) {
        User user = userRepository.findById(userId)
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        commentService.deleteComment(id, user);

        return "redirect:/posts/" + postId;
    }

}
