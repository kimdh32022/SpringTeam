package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Category;
import com.busanit501.bootproject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Enum 타입으로 카테고리 검색
    Page<Post> findByCategory(Category category, Pageable pageable);
}
