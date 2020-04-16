package com.steven.hicks.models.dtos;

import com.fasterxml.jackson.databind.JsonNode;
import com.steven.hicks.models.Review;

public class AlbumWithReviewDTO {

    private JsonNode album;
    private Double averageRating;
    private Review review;

    public AlbumWithReviewDTO(JsonNode album, Double averageRating, Review review) {
        this.album = album;
        this.averageRating = averageRating;
        this.review = review;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        review = review;
    }

    public JsonNode getAlbum() {
        return album;
    }

    public void setAlbum(JsonNode album) {
        this.album = album;
    }
}
