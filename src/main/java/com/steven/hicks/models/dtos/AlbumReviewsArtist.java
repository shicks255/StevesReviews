package com.steven.hicks.models.dtos;

import com.steven.hicks.models.Review;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.artist.Artist;

import java.util.List;

public class AlbumReviewsArtist {

    private Artist m_artist;
    private List<ReviewDTO> m_reviews;
    private Double rating;
    private Album m_album;
    private ReviewDTO m_loggedInUserReview;

    public AlbumReviewsArtist(Artist artist, List<ReviewDTO> reviews, Double rating, Album album, ReviewDTO userReview) {
        m_artist = artist;
        m_reviews = reviews;
        this.rating = rating;
        m_album = album;
        this.m_loggedInUserReview = userReview;
    }

    public Artist getArtist() {
        return m_artist;
    }

    public void setArtist(Artist artist) {
        m_artist = artist;
    }

    public List<ReviewDTO> getReviews() {
        return m_reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        m_reviews = reviews;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Album getAlbum() {
        return m_album;
    }

    public void setAlbum(Album album) {
        m_album = album;
    }

    public ReviewDTO getLoggedInUserReview() {
        return m_loggedInUserReview;
    }

    public void setLoggedInUserReview(ReviewDTO loggedInUserReview) {
        m_loggedInUserReview = loggedInUserReview;
    }
}
