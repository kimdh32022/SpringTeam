package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.domain.Category;
import com.busanit501.bootproject.domain.Comment;
import com.busanit501.bootproject.domain.Post;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.CommentDTO;
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

    // 글쓰기 GET
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/register";
    }
//
//// 글쓰기 Post -> 로그인 필요함 이걸로 할거임 나중에
//@PostMapping("/register")
//public String registerPost(@RequestParam("title") String title,
//                           @RequestParam("content") String content,
//                           @RequestParam("category") String category,
//                           @RequestParam("file") MultipartFile file,
//                           Principal principal) {
//
//    if (principal == null) {
//        throw new IllegalStateException("로그인이 필요합니다.");
//    }
//
//    Users user = usersRepository.findByEmail(principal.getName())
//            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));
//
//    String imageUrl = null;
//
//    if (!file.isEmpty()) {
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        Path filePath = Paths.get("uploads/" + fileName);
//        try {
//            Files.write(filePath, file.getBytes());
//            imageUrl = "/uploads/" + fileName;
//        } catch (IOException ex) {
//            throw new RuntimeException("파일 업로드 실패", ex);
//        }
//    }
//
//    Post post = Post.builder()
//            .title(title)
//            .content(content)
//            .category(Category.valueOf(category))
//            .user(user)
//            .imageUrl(imageUrl)
//            .build();
//
//    postService.createPost(post);
//    return "redirect:/posts";
//}

    // 테스트용 임시로 유저데이터 이용해서 테스트. 이거 나중에 버릴거에요
@PostMapping("/register")
public String registerPost(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("category") String category,
                           @RequestParam("file") MultipartFile file) {

    User user = userRepository.findById(1L)
            .orElseThrow(() -> new NoSuchElementException("테스트 유저(User ID 1)가 존재하지 않습니다."));

    String imageUrl = null;

    // 절대 경로로 uploads 디렉터리 설정
    String uploadDir = "uploads";
    Path uploadPath = Paths.get(uploadDir);  // Path 객체로 변환

    // uploads 디렉터리 생성 (존재하지 않는 경우)
    if (!Files.exists(uploadPath)) {
        try {
            Files.createDirectories(uploadPath);
            System.out.println("uploads 폴더 생성 완료: " + uploadPath.toAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException("uploads 디렉터리 생성 실패", ex);
        }
    }

    // 파일 저장 로직
    if (!file.isEmpty()) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);  // 파일 저장 경로 설정
        try {
            Files.write(filePath, file.getBytes());
            imageUrl = "/uploads/" + fileName;  // 웹 접근 URL
            System.out.println("File uploaded to: " + filePath.toAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException("파일 업로드 실패", ex);
        }
    }

    // 게시글 저장
    Post post = Post.builder()
            .title(title)
            .content(content)
            .category(Category.valueOf(category))
            .user(user)
            .imageUrl(imageUrl)
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
        Page<Post> postsPage = postService.getAllPosts(pageable);

        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        return "posts/list";
    }

    // 📌 게시글 상세 조회 (댓글 포함)
    @GetMapping("/{id}")
    public String getPostDetail(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        List<Comment> comments = commentService.getCommentsByPost(id);

        // 가정: 로그인된 사용자의 ID
        Long loggedInUserId = 1L;

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("loggedInUserId", loggedInUserId);

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
