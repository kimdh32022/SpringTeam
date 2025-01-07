package com.busanit501.bootproject.service.post;

import com.busanit501.bootproject.domain.Category;
import com.busanit501.bootproject.domain.Comment;
import com.busanit501.bootproject.domain.Post;
import com.busanit501.bootproject.dto.CommentDTO;
import com.busanit501.bootproject.dto.PostDTO;
import com.busanit501.bootproject.repository.PostRepository;
import com.busanit501.bootproject.service.comment.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;  // 댓글 서비스 추가

    public PostServiceImpl(PostRepository postRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.commentService = commentService;  // 주입
    }

    // 게시글 전체 조회 (페이징)
    @Override
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    // 게시글 단일 조회
    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return convertToDTO(post);
    }

    // 게시글 등록
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .category(Category.valueOf(postDTO.getCategory()))
                .imageUrl(postDTO.getImageUrl())
                .build();

        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    // 게시글 수정
    @Override
    @Transactional
    public PostDTO editPost(Long id, PostDTO updatedPostDTO) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 새로운 Post 엔티티를 DTO에서 생성
        Post updatedPost = Post.builder()
                .postId(existingPost.getPostId())  // 기존 ID 유지
                .user(existingPost.getUser())      // 기존 작성자 유지
                .title(updatedPostDTO.getTitle())
                .content(updatedPostDTO.getContent())
                .category(Category.valueOf(updatedPostDTO.getCategory()))
                .imageUrl(updatedPostDTO.getImageUrl() != null ? updatedPostDTO.getImageUrl() : existingPost.getImageUrl())
                .build();

        // 새로운 엔티티 저장
        Post savedPost = postRepository.save(updatedPost);
        return convertToDTO(savedPost);
    }

    // 게시글 삭제
    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // 특정 카테고리 게시글 조회 (페이징)
    @Override
    public Page<PostDTO> getPostsByCategory(Category category, Pageable pageable) {
        return postRepository.findByCategory(category, pageable)
                .map(this::convertToDTO);
    }

    // Post -> PostDTO 변환 메서드
    private PostDTO convertToDTO(Post post) {
        // 댓글 조회 및 변환
        List<CommentDTO> commentDTOs = commentService.getCommentsByPost(post.getPostId())
                .stream()
                .map(this::convertCommentToDTO)
                .collect(Collectors.toList());

        return PostDTO.builder()
                .postId(post.getPostId())
                .userId(post.getUser().getUserId())
                .userName(post.getUser().getName())
                .category(post.getCategory().name())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .created_at(post.getCreated_at())
                .updated_at(post.getUpdated_at())
                .comments(commentDTOs)  // 댓글 리스트 추가
                .build();
    }

    // Comment -> CommentDTO 변환 메서드
    private CommentDTO convertCommentToDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUser().getUserId())
                .userName(comment.getUser().getName())
                .content(comment.getContent())
                .created_at(comment.getCreated_at())
                .build();
    }
}