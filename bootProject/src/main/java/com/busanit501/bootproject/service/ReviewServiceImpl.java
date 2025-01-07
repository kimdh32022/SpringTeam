package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.Review;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.ReviewDTO;
import com.busanit501.bootproject.repository.ReviewRepository;
import com.busanit501.bootproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        User reviewer = userRepository.findById(reviewDTO.getReviewerId())
                .orElseThrow(() -> new RuntimeException("리뷰 작성자가 존재하지 않습니다."));

        User reviewed = userRepository.findById(reviewDTO.getReviewedId())
                .orElseThrow(() -> new RuntimeException("리뷰 대상 사용자가 존재하지 않습니다."));

        Review review = Review.builder()
                .reviewer(reviewer)
                .reviewed(reviewed)
                .reason(reviewDTO.getReason())
                .status(Review.ReviewStatus.PENDING)
                .build();

        Review savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 리뷰를 찾을 수 없습니다."));
        return convertToDTO(review);
    }

    @Override
    public Page<ReviewDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public ReviewDTO updateReview(Long id, ReviewDTO updatedReviewDTO) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));

        existingReview.updateReason(updatedReviewDTO.getReason());
        existingReview.updateStatus(Review.ReviewStatus.valueOf(updatedReviewDTO.getStatus()));

        Review updatedReview = reviewRepository.save(existingReview);
        return convertToDTO(updatedReview);
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("해당 리뷰를 찾을 수 없습니다.");
        }
        reviewRepository.deleteById(id);
    }

    // 엔티티 -> DTO 변환 메서드
    private ReviewDTO convertToDTO(Review review) {
        return ReviewDTO.builder()
                .reviewId(review.getReviewId())
                .reviewerId(review.getReviewer().getUserId())
                .reviewedId(review.getReviewed().getUserId())
                .reviewerName(review.getReviewer().getName())
                .reviewedName(review.getReviewed().getName())
                .reason(review.getReason())
                .status(review.getStatus().name())
                .createdAt(review.getCreated_at())  // BaseEntity에서 값 가져오기
                .updatedAt(review.getUpdated_at())  // BaseEntity에서 값 가져오기
                .build();
    }
}