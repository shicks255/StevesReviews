package com.steven.hicks.models.dtos;

import com.fasterxml.jackson.databind.JsonNode;

public class ReviewWithAlbum {

    private JsonNode album;
    private JsonNode review;
    private JsonNode artist;

    public ReviewWithAlbum(JsonNode album, JsonNode review, JsonNode artist) {
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

    public JsonNode getReview() {
        return review;
    }

    public void setReview(JsonNode review) {
        this.review = review;
    }

    public JsonNode getArtist() {
        return artist;
    }

    public void setArtist(JsonNode artist) {
        this.artist = artist;
    }
}