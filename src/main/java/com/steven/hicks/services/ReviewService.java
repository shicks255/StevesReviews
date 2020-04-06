package com.steven.hicks.services;

import com.steven.hicks.models.Review;
import com.steven.hicks.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository m_reviewRepository;

    public List<Review> getRecentReviews() {
        return m_reviewRepository.findAll();
    }
}
