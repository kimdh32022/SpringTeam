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

    // 생성자 주입을 통해 서비스 및 레포지토리 초기화
    public PostController(PostService postService, UserRepository userRepository, CommentService commentService) {
        this.postService = postService;
        this.userRepository = userRepository;
        this.commentService = commentService;
    }

    // 게시글 작성 페이지 GET 요청 (등록 폼 출력)
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("post", new PostDTO());
        return "posts/register";  // 게시글 등록 페이지 반환
    }

    // 게시글 등록 POST 요청 (로그인 필수)
    @PostMapping("/register")
    public String registerPost(@RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam("category") String category,
                               @RequestParam("file") MultipartFile file,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {

        // 사용자가 로그인하지 않은 경우
        if (principal == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        // 현재 로그인된 사용자 조회
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 사용자입니다.");
            return "redirect:/login";
        }

        // 파일 업로드 처리
        String imageUrl = null;
        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get("uploads/" + fileName);
            try {
                Files.write(filePath, file.getBytes());
                imageUrl = "/uploads/" + fileName;  // 이미지 URL 저장
            } catch (IOException ex) {
                throw new RuntimeException("파일 업로드 실패", ex);
            }
        }

        // 게시글 정보 DTO로 생성
        PostDTO postDTO = PostDTO.builder()
                .title(title)
                .content(content)
                .category(category)
                .userId(user.getUserId())
                .userName(user.getName())
                .imageUrl(imageUrl)
                .build();

        postService.createPost(postDTO);
        return "redirect:/posts";  // 게시글 목록으로 리다이렉트
    }

    // 게시글 목록 조회 (페이징 처리)
    @GetMapping
    public String listPosts(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> postsPage = postService.getAllPosts(pageable);

        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        return "posts/list";
    }

    // 게시글 상세 조회 (댓글 포함)
    @GetMapping("/{id}")
    public String getPostDetail(@PathVariable Long id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        // 게시글 및 댓글 조회
        PostDTO post = postService.getPostById(id);
        List<Comment> comments = commentService.getCommentsByPost(id);

        // 로그인 확인
        if (principal == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        // 현재 로그인된 사용자 조회
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 사용자입니다.");
            return "redirect:/login";
        }

        Long loggedInUserId = user.getUserId();

        // 모델에 게시글과 댓글 추가
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("loggedInUserId", loggedInUserId);
        return "posts/detail";
    }

    // 게시글 수정 폼 조회
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        PostDTO post = postService.getPostById(id);

        // 게시글이 없는 경우
        if (post == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "수정할 게시글이 존재하지 않습니다.");
            return "redirect:/posts";
        }

        model.addAttribute("post", post);
        return "posts/edit";  // 수정 페이지로 이동
    }

    // 게시글 수정 처리
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("category") String category,
                           @RequestParam(value = "file", required = false) MultipartFile file) {

        PostDTO existingPost = postService.getPostById(id);
        existingPost.setTitle(title);
        existingPost.setContent(content);
        existingPost.setCategory(category);

        // 파일 수정 시 처리
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get("uploads");
            try {
                Files.write(filePath.resolve(fileName), file.getBytes());
                existingPost.setImageUrl("/uploads/" + fileName);
            } catch (IOException ex) {
                throw new RuntimeException("파일 업로드 실패", ex);
            }
        }

        postService.editPost(id, existingPost);
        return "redirect:/posts";
    }
}