package com.steven.hicks.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.models.dtos.ReviewWithAlbum;
import com.steven.hicks.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository m_reviewRepository;

    private ObjectMapper m_objectMapper = new ObjectMapper();
    private MBAlbumSearcher m_mbAlbumSearcher = new MBAlbumSearcher();

    public List<ReviewWithAlbum> getRecentReviews() {
        List<ReviewDTO> reviews = m_reviewRepository.findAll().subList(0, 3)
                .stream()
                .map(x -> new ReviewDTO(x))
                .collect(Collectors.toList());

        List<ReviewWithAlbum> albumsWithReview = reviews.stream().map(x -> {
            try
            {
                String review = m_objectMapper.writeValueAsString(x);
                JsonNode album = m_mbAlbumSearcher.getAlbum(x.getAlbumId());
                return new ReviewWithAlbum(album, m_objectMapper.readTree(review));
            } catch (JsonProcessingException | NullPointerException e)
            {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        return albumsWithReview;
    }

    public List<ReviewDTO> getReviewsForAlbum(String albumId) {
        List<ReviewDTO> reviews =  m_reviewRepository.findAllByAlbumId(albumId)
                .stream()
                .map(x -> new ReviewDTO(x))
                .collect(Collectors.toList());

        return reviews;
    }

    public Double getAverageRating(String albumId) {
        return m_reviewRepository.getAverageRating(albumId);
    }
}
