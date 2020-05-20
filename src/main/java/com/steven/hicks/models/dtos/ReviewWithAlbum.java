package com.steven.hicks.models.dtos;

import com.fasterxml.jackson.databind.JsonNode;

public class ReviewWithAlbum {

    private JsonNode album;
    private ReviewDTO review;
    private JsonNode artist;

    public ReviewWithAlbum(JsonNode album, ReviewDTO review, JsonNode artist) {
        this.album = album;
        this.review = review;
        this.artist = artist;
    }

    public JsonNode getAlbum() {
        return album;
    }

    public void setAlbum(JsonNode album) {
        this.album = album;
    }

    public ReviewDTO getReview() {
        return review;
    }

    public void setReview(ReviewDTO review) {
        this.review = review;
    }

    public JsonNode getArtist() {
        return artist;
    }

    public void setArtist(JsonNode artist) {
        this.artist = artist;
    }
}