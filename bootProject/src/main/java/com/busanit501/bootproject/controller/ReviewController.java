package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.domain.Review;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        UserDTO userDTO;
        if (loginUser != null) {
            userDTO = UserDTO.builder()
                    .userId(loginUser.getUserId())
                    .email(loginUser.getEmail())
                    .name(loginUser.getName())
                    .birth(loginUser.getBirth())
                    .gender(loginUser.getGender())
                    .address(loginUser.getAddress())
                    .profilePicture(loginUser.getProfilePicture())
                    .phoneNumber(loginUser.getPhoneNumber())
                    .rating(loginUser.getRating())
                    .ratingCount(loginUser.getRatingCount())
                    .build();
        } else {
            userDTO = UserDTO.builder()
                    .name("Guest")
                    .profilePicture("/images/default-profile.png")
                    .build();
        }

        model.addAttribute("user", userDTO);
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