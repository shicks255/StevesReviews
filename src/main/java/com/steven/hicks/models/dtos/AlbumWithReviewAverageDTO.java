package com.steven.hicks.models.dtos;

import com.steven.hicks.models.Review;
import com.steven.hicks.models.album.Album;

public class AlbumWithReviewAverageDTO {

    private Album album;
    private Review review;
    private String imageUrl;
    private double averageRating;

    public AlbumWithReviewAverageDTO(Album album, Review review, String imageUrl, double averageRating) {
        this.album = album;
        this.review = review;
        this.imageUrl = imageUrl;
        this.averageRating = averageRating;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
