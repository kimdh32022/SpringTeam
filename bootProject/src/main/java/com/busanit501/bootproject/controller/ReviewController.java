package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.domain.Review;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 특정 매칭 방의 리뷰 목록 조회
    @GetMapping("/{roomId}")
    @ResponseBody
    public List<Review> getReviewsByRoom(@PathVariable Integer roomId) {
        return reviewService.getReviewsByRoomId(roomId);
    }

    // 리뷰 작성 화면
    @GetMapping
    public String showReviewForm(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser != null) {
            model.addAttribute("user", loginUser);
        } else {
            User defaultUser = new User();
            defaultUser.setName("Guest");
            defaultUser.setProfilePicture("/images/default-profile.png");  // 기본 이미지 설정
            model.addAttribute("user", defaultUser);
        }
        return "review";
    }

    // 리뷰 제출 처리
    @PostMapping
    @ResponseBody
    public String submitReview(@RequestBody Review review, HttpSession session) {
        Long reviewerId = ((User) session.getAttribute("loginUser")).getUserId();
        reviewService.submitReview(
                (long) review.getMatchingRoom().getRoomId(),  // int를 long으로 캐스팅
                reviewerId,
                review.getReviewed().getUserId(),
                review.getRating(),
                review.getComment()
        );
        return "success";
    }
}