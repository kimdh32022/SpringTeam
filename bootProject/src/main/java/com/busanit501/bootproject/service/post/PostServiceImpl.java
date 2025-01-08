package com.busanit501.bootproject.service.post;

import com.busanit501.bootproject.domain.Category;
import com.busanit501.bootproject.domain.Post;
import com.busanit501.bootproject.domain.Users;
import com.busanit501.bootproject.dto.PostWithCommentDTO;
import com.busanit501.bootproject.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Page<PostWithCommentDTO> getAllPosts(Pageable pageable) {
        Page<Object[]> results = postRepository.findAllWithUserAndCommentCount(pageable);

        return results.map(row -> {
            Post post = (Post) row[0];
            Users user = (Users) row[1];
            Long commentCount = (Long) row[2];

            return new PostWithCommentDTO(
                    post.getPostId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCategory(),
                    user != null ? user.getName() : "작성자 없음",  // 사용자 정보 없을 경우 처리
                    user != null ? user.getAddress() : "주소 미등록",
                    post.getCreated_at(),
                    commentCount
            );
        });
    }


    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElse(null);  // 게시글이 없으면 null 반환
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post editPost(Long id, Post updatedPost) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setCategory(updatedPost.getCategory());

        if (updatedPost.getImageUrl() != null) {
            existingPost.setImageUrl(updatedPost.getImageUrl());
        }

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Page<Post> getPostsByCategory(Category category, Pageable pageable) {
        return postRepository.findByCategory(category, pageable);
    }
}
