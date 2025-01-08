package com.busanit501.bootproject.service.post;

import com.busanit501.bootproject.domain.Category;
import com.busanit501.bootproject.domain.Post;
import com.busanit501.bootproject.dto.PostWithCommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostWithCommentDTO> getAllPosts(Pageable pageable);
    Post getPostById(Long id);
    Post createPost(Post post);
    Post editPost(Long id, Post post);
    void deletePost(Long id);
    Page<Post> getPostsByCategory(Category category, Pageable pageable);

}
