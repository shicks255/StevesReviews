package com.steven.hicks.controllers;

import com.steven.hicks.models.User;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.models.dtos.ReviewWithAlbum;
import com.steven.hicks.services.ReviewService;
import com.steven.hicks.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<ReviewWithAlbum> getRecentReview() {
        return m_reviewService.getRecentReviews();
    }

    @GetMapping("/user")
    public List<ReviewWithAlbum> getUserReviews() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return m_reviewService.getReviewsByUser(user.getId());
    }
}
