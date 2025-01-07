package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.domain.Review;
import com.busanit501.bootproject.domain.User;
import com.busanit501.bootproject.dto.ReviewDTO;
import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public String listReviews(Model model, @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Page<ReviewDTO> reviews = reviewService.getAllReviews(PageRequest.of(page, size));
        model.addAttribute("reviews", reviews);
        model.addAttribute("currentPage", page);
        return "reviews/list";
    }

    @GetMapping("/{id}")
    public String getReviewDetail(@PathVariable Long id, Model model) {
        ReviewDTO review = reviewService.getReviewById(id);
        model.addAttribute("review", review);
        return "reviews/detail";
    }

    @PostMapping("/register")
    public String createReview(@ModelAttribute ReviewDTO reviewDTO) {
        reviewService.createReview(reviewDTO);
        return "redirect:/reviews";
    }

    @PostMapping("/edit/{id}")
    public String updateReview(@PathVariable Long id, @ModelAttribute ReviewDTO reviewDTO) {
        reviewService.updateReview(id, reviewDTO);
        return "redirect:/reviews";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "success";
    }
}