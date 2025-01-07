package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    @Override
    public void submitReview(Long roomId, Long reviewerId, Long reviewedId, Float rating, String comment) {
        Review review = new Review();
        review.setMatchingRoomId(roomId);
        review.setReviewerId(reviewerId);
        review.setReviewedId(reviewedId);
        review.setRating(rating);
        review.setComment(comment);

        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByRoomId(Integer roomId) {
        return reviewRepository.findByMatchingRoomId(roomId);
    }
}