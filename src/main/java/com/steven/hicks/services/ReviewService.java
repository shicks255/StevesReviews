package com.steven.hicks.services;

import com.steven.hicks.models.Review;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository m_reviewRepository;

    public List<ReviewDTO> getRecentReviews() {
        return m_reviewRepository.findAll().subList(0, 3)
                .stream()
                .map(x -> new ReviewDTO(x))
                .collect(Collectors.toList());
    }
}
