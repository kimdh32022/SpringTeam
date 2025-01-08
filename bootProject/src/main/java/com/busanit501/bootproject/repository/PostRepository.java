package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Category;
import com.busanit501.bootproject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글 목록 조회 (User와 댓글 수 포함)
    @Query("SELECT p, u, COUNT(c) " +
            "FROM Post p " +
            "LEFT JOIN FETCH p.user u " +  // User 정보 즉시 로딩
            "LEFT JOIN Comment c ON c.post.postId = p.postId " +
            "GROUP BY p, u")  // User도 Group by에 추가
    Page<Object[]> findAllWithUserAndCommentCount(Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);
}
