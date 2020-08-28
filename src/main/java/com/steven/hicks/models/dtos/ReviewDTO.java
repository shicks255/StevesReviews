package com.steven.hicks.models.dtos;

import com.steven.hicks.models.Review;
import com.steven.hicks.models.artist.Artist;

import java.time.format.DateTimeFormatter;

public class ReviewDTO {

    private static DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private Artist artist;
    private Review review;
    private String colorClass;
    private Double rating;

    public ReviewDTO(Review review) {

        this.artist = review.getAlbum().getArtist();
        this.artist.getAlbums().clear();
        this.review = review;

        if (review.getRating() >= 3.5)
            this.colorClass = "is-success";
        else if (review.getRating() >= 2.0)
            this.colorClass = "is-warning";
        else if (review.getRating() < 2.0)
            this.colorClass = "is-danger";
        else
            this.colorClass = "is-light";
    }

    public String getColorClass() {
        return colorClass;
    }

    public void setColorClass(String colorClass) {
        this.colorClass = colorClass;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
