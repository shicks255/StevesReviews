package com.steven.hicks.services;

import com.steven.hicks.models.Review;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.repositories.AlbumRepository;
import com.steven.hicks.repositories.ArtistRepository;
import com.steven.hicks.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository m_reviewRepository;
    @Autowired
    AlbumRepository m_albumRepository;
    @Autowired
    ArtistRepository m_artistRepository;

    public List<ReviewDTO> getRecentReviews() {
        List<ReviewDTO> dto =  m_reviewRepository.findTop5ByOrderByAddedOnDesc()
                .stream()
                .map(x -> new ReviewDTO(x))
                .collect(Collectors.toList());

        return dto;
    }

    public List<ReviewDTO> getReviewsForAlbum(String albumId) {
        List<ReviewDTO> reviews =  m_reviewRepository.findAllByAlbumId(albumId)
                .stream()
                .map(x -> new ReviewDTO(x))
                .collect(Collectors.toList());

        return reviews;
    }

    public List<ReviewDTO> getReviewsByUser(int userId) {
        return m_reviewRepository.findAllByUserIdOrderByAddedOnDesc(userId)
                .stream()
                .map(x -> new ReviewDTO(x))
                .collect(Collectors.toList());
    }

    public Double getAverageRating(String albumId) {
        return m_reviewRepository.getAverageRating(albumId);
    }

    public Review findFirstByAlbumIdOrderByRatingDesc(String albumId) {
        return m_reviewRepository.findFirstByAlbumIdOrderByRatingDesc(albumId);
    }

    public void updateReview(Review review) {
        m_reviewRepository.update(review.getContent(), review.getRating(), LocalDateTime.now(ZoneOffset.UTC), review.getId());
    }

    public void saveReview(Review review) {
        m_reviewRepository.save(review);
    }
}
