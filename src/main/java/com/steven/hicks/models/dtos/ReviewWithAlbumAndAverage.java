package com.steven.hicks.models.dtos;

public class ReviewWithAlbumAndAverage {

    private ReviewWithAlbum m_reviewWithAlbum;
    private Double average;
    private String colorClass;

    public ReviewWithAlbumAndAverage(ReviewWithAlbum reviewWithAlbum, Double average) {
        this.m_reviewWithAlbum = reviewWithAlbum;
        this.average = average;

        if (this.average >= 3.5)
            this.colorClass = "is-success";
        else if (this.average >= 2.0)
            this.colorClass = "is-warning";
        else if (this.average < 2.0)
            this.colorClass = "is-danger";
        else
            this.colorClass = "is-light";
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

    public String getColorClass() {
        return colorClass;
    }

    public void setColorClass(String colorClass) {
        this.colorClass = colorClass;
    }
}
