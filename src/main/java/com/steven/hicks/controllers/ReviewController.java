package com.steven.hicks.controllers;

import com.steven.hicks.aspects.Logged;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.User;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.services.JwtTokenService;
import com.steven.hicks.services.ReviewService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewService m_reviewService;
    @Autowired
    JwtTokenService m_jwtTokenService;

    @GetMapping("/recent")
    @Logged
    @Timed()
    public List<ReviewDTO> getRecentReview() {
        return m_reviewService.getRecentReviews();
    }

    @GetMapping("/user")
    @Logged
    @Timed()
    public List<ReviewDTO> getUserReviews(HttpServletRequest request) {
        User user = m_jwtTokenService.getUserFromToken(request);
        return m_reviewService.getReviewsByUser(user.getId());
    }

    @GetMapping("/user/{userId}")
    @Logged
    @Timed()
    public List<ReviewDTO> getUserReviews(@PathVariable int userId) {
        return m_reviewService.getReviewsByUser(userId);
    }

    @PostMapping("/upsert")
    @Logged
    @Timed()
    public void updateReview(@RequestBody  Review review, HttpServletRequest request) {
        User user = m_jwtTokenService.getUserFromToken(request);
        if (review.getId() == 0)
        {
            review.setUser(user);
            review.setAddedOn(LocalDateTime.now(ZoneOffset.UTC));
            m_reviewService.saveReview(review);
        }
        else
        {
            review.setLastUpdated(LocalDateTime.now());
            m_reviewService.updateReview(review);
        }
    }
}
