package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.domain.Category;
import com.busanit501.bootproject.domain.Comment;
import com.busanit501.bootproject.domain.Post;
import com.busanit501.bootproject.domain.Users;
import com.busanit501.bootproject.dto.CommentDTO;
import com.busanit501.bootproject.dto.PostWithCommentDTO;
import com.busanit501.bootproject.repository.UsersRepository;
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
    private final UsersRepository usersRepository;
    private final CommentService commentService;

    public PostController(PostService postService, UsersRepository usersRepository, CommentService commentService) {
        this.postService = postService;
        this.usersRepository = usersRepository;
        this.commentService = commentService;
    }

    // 글쓰기 GET
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/register";
    }

    // 게시글 등록 Post
    @PostMapping("/register")
    public String registerPost(@RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam("category") String category,
                               @RequestParam("file") MultipartFile file) {

        // 사용자 조회 (기본 이메일로 테스트 유저 조회)
        Users user = usersRepository.findByEmail("test@example.com")
                .orElseThrow(() -> new NoSuchElementException("테스트 유저(email: test@example.com)가 존재하지 않습니다. 기본 작성자가 설정됩니다."));

        // 기본 이미지 URL 설정
        String imageUrl = null;

        // 파일 업로드 경로 설정
        String uploadDir = "uploads";
        Path uploadPath = Paths.get(uploadDir);

        // uploads 디렉터리 생성 (존재하지 않는 경우)
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
                System.out.println("uploads 폴더 생성 완료: " + uploadPath.toAbsolutePath());
            } catch (IOException ex) {
                throw new RuntimeException("uploads 디렉터리 생성 실패", ex);
            }
        }

        // 파일이 비어있지 않은 경우에만 업로드 처리
        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            try {
                Files.write(filePath, file.getBytes());
                imageUrl = "/uploads/" + fileName;  // 파일 저장 경로를 웹 경로로 반환
                System.out.println("File uploaded to: " + filePath.toAbsolutePath());
            } catch (IOException ex) {
                throw new RuntimeException("파일 업로드 실패", ex);
            }
        }

        // 게시글 저장 (user와 imageUrl이 포함된 상태로 저장)
        Post post = Post.builder()
                .title(title)
                .content(content)
                .category(Category.valueOf(category))
                .user(user)  // 사용자 설정 (필수)
                .imageUrl(imageUrl)  // 업로드된 이미지 URL 저장
                .build();

        postService.createPost(post);
        return "redirect:/posts";
    }


    // 📌 게시글 목록 조회 (페이징)
    @GetMapping
    public String listPosts(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostWithCommentDTO> postsPage = postService.getAllPosts(pageable);

        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        return "posts/list";
    }

    // 📌 게시글 상세 조회 (댓글 포함)
//    @GetMapping("/{id}")
//    public String getPostDetail(@PathVariable Long id, Model model, Principal principal) {
//        Post post = postService.getPostById(id);
//        List<Comment> comments = commentService.getCommentsByPost(id);
//
//        Long loggedInUserId = null;
//        if (principal != null) {
//            Users loggedInUser = usersRepository.findByEmail(principal.getName())
//                    .orElseThrow(() -> new NoSuchElementException("로그인된 사용자가 존재하지 않습니다."));
//            loggedInUserId = loggedInUser.getUserId();
//        }
//
//        model.addAttribute("post", post);
//        model.addAttribute("comments", comments);
//        model.addAttribute("loggedInUserId", loggedInUserId);
//
//        return "posts/detail";
//    }

    //테스트용 하드코딩
    @GetMapping("/{id}")
    public String getPostDetail(@PathVariable Long id,
                                @RequestParam(defaultValue = "0") int page,  // 페이지 정보 받기
                                Model model) {
        Post post = postService.getPostById(id);
        List<Comment> comments = commentService.getCommentsByPost(id);

        // 임시로 로그인 사용자 ID를 1로 설정 (실제 테스트하고자 하는 사용자 ID로 수정)
        Long loggedInUserId = 1L;  // 예: 테스트 사용자 ID

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("loggedInUserId", loggedInUserId);
        model.addAttribute("page", page);  // 페이지 정보 추가

        return "posts/detail";  // detail.html 템플릿으로 연결
    }


    // 📌 게시글 수정 페이지
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Post post = postService.getPostById(id);

        if (post == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "수정할 게시글이 존재하지 않습니다.");
            return "redirect:/posts";
        }

        model.addAttribute("post", post);
        return "posts/edit";
    }


    // 📌 게시글 수정 처리
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("category") String category,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "file", required = false) MultipartFile file) {

        int currentPage = (page != null) ? page : 0;  // 안전하게 처리

        Post existingPost = postService.getPostById(id);

        existingPost.setTitle(title);
        existingPost.setContent(content);
        existingPost.setCategory(Category.valueOf(category));

        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get("Togedog/bootProject/src/main/resources/static/uploads");

            try {
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, file.getBytes());
                existingPost.setImageUrl("/uploads/" + fileName);
            } catch (IOException ex) {
                throw new RuntimeException("파일 업로드 실패", ex);
            }
        }

        postService.editPost(id, existingPost);

        // 페이지 정보 유지
        return "redirect:/posts?page=" + currentPage;
    }

    // 📌 게시글 삭제 처리 (AJAX 요청)
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deletePost(@PathVariable Long id) {
        log.info("Delete 요청: " + id);
        postService.deletePost(id);
        return "success";
    }


    // 동물병원 페이지
    @GetMapping("/category/hospital")
    public String hospitalPage(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postService.getPostsByCategory(Category.EmergencyHospital, pageable);

        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        return "posts/hospital";
    }


    // 중고 장터 페이지
    @GetMapping("/category/useditems")
    public String usedItemsPage(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postService.getPostsByCategory(Category.UsedItems, pageable);

        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        return "posts/useditems";
    }

}
