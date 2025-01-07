package com.busanit501.bootproject.service.post;

import com.busanit501.bootproject.domain.Category;
import com.busanit501.bootproject.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDTO> getAllPosts(Pageable pageable);
    PostDTO getPostById(Long id);
    PostDTO createPost(PostDTO postDTO);
    PostDTO editPost(Long id, PostDTO updatedPostDTO);
    void deletePost(Long id);
    Page<PostDTO> getPostsByCategory(Category category, Pageable pageable);
}