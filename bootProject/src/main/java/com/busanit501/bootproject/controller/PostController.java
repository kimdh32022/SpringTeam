package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.domain.Category;
import com.busanit501.bootproject.domain.Comment;
import com.busanit501.bootproject.domain.Post;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.CommentDTO;
import com.busanit501.bootproject.dto.PostDTO;
import com.busanit501.bootproject.repository.UserRepository;
import com.busanit501.bootproject.service.comment.CommentService;
import com.busanit501.bootproject.service.post.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Log4j2
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final CommentService commentService;

    public PostController(PostService postService, UserRepository userRepository, CommentService commentService) {
        this.postService = postService;
        this.userRepository = userRepository;
        this.commentService = commentService;
    }

    @GetMapping
    public String listPosts(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<PostDTO> postsPage = postService.getAllPosts(pageable);
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        return "posts/list";
    }

    // 게시글 등록
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("post", new PostDTO());
        return "posts/register";  // posts/register.html 반환
    }

    // 게시글 상세 조회 (숫자 ID만 허용)
    @GetMapping("/{id:\\d+}")
    public String getPostDetail(@PathVariable Long id, Model model) {
        try {
            PostDTO post = postService.getPostById(id);
            List<Comment> comments = commentService.getCommentsByPost(id);
            model.addAttribute("post", post);
            model.addAttribute("comments", comments);
        } catch (Exception e) {
            return "error/404";
        }
        return "posts/detail";
    }

    // 병원 관련 게시판
    @GetMapping("/hospital")
    public String hospitalList(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PostDTO> postsPage = postService.getPostsByCategory(Category.EmergencyHospital, pageable);
        model.addAttribute("postsPage", postsPage);
        return "posts/hospital";
    }

    // 중고거래 게시판
    @GetMapping("/useditems")
    public String usedItemList(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<PostDTO> postsPage = postService.getPostsByCategory(Category.UsedItems, pageable);

        int totalPages = postsPage.getTotalPages();
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages > 0 ? totalPages : 1);  // totalPages 기본값 1 설정
        return "posts/useditems";
    }
}