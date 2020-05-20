package com.steven.hicks.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.models.dtos.ReviewWithAlbum;
import com.steven.hicks.repositories.AlbumRepository;
import com.steven.hicks.repositories.ArtistRepository;
import com.steven.hicks.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private ObjectMapper m_objectMapper = new ObjectMapper();

    public List<ReviewWithAlbum> getRecentReviews() {
        List<ReviewDTO> reviews = m_reviewRepository.findByOrderByAddedOnDesc().subList(0, 3)
                .stream()
                .map(x -> new ReviewDTO(x))
                .collect(Collectors.toList());

        List<ReviewWithAlbum> albumsWithReview = reviews.stream().map(x -> {
            try
            {
                Album album = m_albumRepository.findById(x.getAlbumId()).get();
                Artist artist = album.getArtist();
                String albumNode = m_objectMapper.writeValueAsString(album);
                String artistNode = m_objectMapper.writeValueAsString(artist);

                return new ReviewWithAlbum(
                        m_objectMapper.readTree(albumNode),
                        x,
                        m_objectMapper.readTree(artistNode)
                );
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
        List<ReviewDTO> reviews = m_reviewRepository.findAllByUserId(userId)
                .stream()
                .map(x -> new ReviewDTO(x))
                .collect(Collectors.toList());

        List<ReviewWithAlbum> rwa = reviews.stream()
                .map(r -> {
                    Album album = m_albumRepository.findById(r.getAlbumId()).get();
                    Artist artist = album.getArtist();
                    try
                    {
                        String albumNode = m_objectMapper.writeValueAsString(album);
                        String artistNode = m_objectMapper.writeValueAsString(artist);

                        return new ReviewWithAlbum(
                                m_objectMapper.readTree(albumNode),
                                r,
                                m_objectMapper.readTree(artistNode)
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

    public void updateReview(Review review) {
        m_reviewRepository.update(review.getContent(), review.getRating(), LocalDateTime.now(), review.getId());
    }

    public void saveReview(Review review) {
        m_reviewRepository.save(review);
    }
}
