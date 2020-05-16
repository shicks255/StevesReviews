package com.steven.hicks.controllers;

import com.steven.hicks.models.Review;
import com.steven.hicks.models.User;
import com.steven.hicks.models.dtos.ReviewWithAlbum;
import com.steven.hicks.services.JwtTokenService;
import com.steven.hicks.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService m_reviewService;
    @Autowired
    JwtTokenService m_jwtTokenService;

    @GetMapping("/recent")
    public List<ReviewWithAlbum> getRecentReview() {
        return m_reviewService.getRecentReviews();
    }

    @GetMapping("/user")
    public List<ReviewWithAlbum> getUserReviews(HttpServletRequest request) {
        User user = m_jwtTokenService.getUserFromToken(request);
        List<ReviewWithAlbum> reviewWithAlbums = m_reviewService.getReviewsByUser(user.getId());
        return reviewWithAlbums;
    }

    @GetMapping("/user/{userId}")
    public List<ReviewWithAlbum> getUserReviews(@PathVariable int userId) {
        List<ReviewWithAlbum> reviewWithAlbums = m_reviewService.getReviewsByUser(userId);
        return reviewWithAlbums;
    }

    @PostMapping("/update")
    public void updateReview(@RequestBody  Review review, HttpServletRequest request) {
        User user = m_jwtTokenService.getUserFromToken(request);
        if (review.getId() == 0)
        {
            review.setUser(user);
            m_reviewService.saveReview(review);
        }
        else
            m_reviewService.updateReview(review);
    }
}
