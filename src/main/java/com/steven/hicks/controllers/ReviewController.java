package com.steven.hicks.controllers;

import com.steven.hicks.models.Review;
import com.steven.hicks.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService m_reviewService;

    @GetMapping("/recent")
    public List<Review> getRecentReview() {
        return m_reviewService.getRecentReviews();
    }
}
