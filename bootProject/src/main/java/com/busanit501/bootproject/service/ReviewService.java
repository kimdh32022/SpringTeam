package com.busanit501.bootproject.service;

import com.busanit501.bootproject.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewService {
    void submitReview(Long roomId, Long reviewerId, Long reviewedId, Float rating, String comment);
    List<Review> getReviewsByRoomId(Integer roomId);
}
