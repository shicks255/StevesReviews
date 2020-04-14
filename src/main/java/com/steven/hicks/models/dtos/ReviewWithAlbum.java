package com.steven.hicks.models.dtos;

import com.fasterxml.jackson.databind.JsonNode;

public class ReviewWithAlbum {

    private JsonNode album;
    private JsonNode review;

    public ReviewWithAlbum(JsonNode album, JsonNode review) {
        this.album = album;
        this.review = review;
    }

    public JsonNode getAlbum() {
        return album;
    }

    public void setAlbum(JsonNode album) {
        this.album = album;
    }

    public JsonNode getReview() {
        return review;
    }

    public void setReview(JsonNode review) {
        this.review = review;
    }
}