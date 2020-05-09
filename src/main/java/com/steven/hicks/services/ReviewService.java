package com.steven.hicks.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.models.dtos.ReviewWithAlbum;
import com.steven.hicks.repositories.AlbumRepository;
import com.steven.hicks.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository m_reviewRepository;
    @Autowired
    AlbumRepository m_albumRepository;

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

    public List<ReviewWithAlbum> getReviewsByUser(int userId) {
        List<Review> reviews = m_reviewRepository.findAllByUserId(userId);

        List<ReviewWithAlbum> rwa = reviews.stream()
                .map(r -> {
                    Album album = m_albumRepository.getOne(r.getAlbumId());
                    try
                    {
                        String albumNode = m_objectMapper.writeValueAsString(album);
                        String reviewNode = m_objectMapper.writeValueAsString(r);

                        return new ReviewWithAlbum(
                                m_objectMapper.readTree(albumNode),
                                m_objectMapper.readTree(reviewNode)
                        );
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
        return rwa;
    }

    public Double getAverageRating(String albumId) {
        return m_reviewRepository.getAverageRating(albumId);
    }

    public Review findFirstByAlbumIdOrderByRatingDesc(String albumId) {
        return m_reviewRepository.findFirstByAlbumIdOrderByRatingDesc(albumId);
    }
}
