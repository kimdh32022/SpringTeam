package com.busanit501.bootproject.controller;


import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.repository.UserRepository;
import com.busanit501.bootproject.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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
                                @RequestParam(required = false) Integer page,
                                Principal principal) {
        if (principal == null) {
            // 로그인되지 않은 경우
            return "redirect:/login";
        }

        // 현재 로그인된 사용자의 이메일로 User 조회
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        // 댓글 생성
        commentService.createComment(postId, user, content);

        return page != null
                ? "redirect:/posts/" + postId + "?page=" + page
                : "redirect:/posts/" + postId;
    }

    // 댓글 삭제
    @PostMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id,
                                Principal principal,
                                @RequestParam Long postId,
                                RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "사용자를 찾을 수 없습니다.");
            return "redirect:/login";
        }

        commentService.deleteComment(id, user);

        return "redirect:/posts/" + postId;
    }
}
