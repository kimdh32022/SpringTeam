package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewer_UserId(Long reviewerId);
    List<Review> findByReviewed_UserId(Long userId);
}