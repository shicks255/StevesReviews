package com.steven.hicks.models.dtos;

public class ReviewWithAlbumAndAverage {

    private ReviewWithAlbum m_reviewWithAlbum;
    private Double average;

    public ReviewWithAlbumAndAverage(ReviewWithAlbum reviewWithAlbum, Double average) {
        this.m_reviewWithAlbum = reviewWithAlbum;
        this.average = average;
    }

    public ReviewWithAlbum getReviewWithAlbum() {
        return m_reviewWithAlbum;
    }

    public void setReviewWithAlbum(ReviewWithAlbum reviewWithAlbum) {
        m_reviewWithAlbum = reviewWithAlbum;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
