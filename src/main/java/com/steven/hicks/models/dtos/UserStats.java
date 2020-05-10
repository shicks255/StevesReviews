package com.steven.hicks.models.dtos;

import java.time.LocalDate;

public class UserStats {

    private int reviews;
    private long firstStarReviews;
    private float averageRating;
    private int averageReviewLength;
    private String lastReview;

    public UserStats(int reviews, long firstStarReviews, float averageRating, int averageReviewLength, String lastReview) {
        this.reviews = reviews;
        this.firstStarReviews = firstStarReviews;
        this.averageRating = averageRating;
        this.averageReviewLength = averageReviewLength;
        this.lastReview = lastReview;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public long getFirstStarReviews() {
        return firstStarReviews;
    }

    public void setFirstStarReviews(long firstStarReviews) {
        this.firstStarReviews = firstStarReviews;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getAverageReviewLength() {
        return averageReviewLength;
    }

    public void setAverageReviewLength(int averageReviewLength) {
        this.averageReviewLength = averageReviewLength;
    }

    public String getLastReview() {
        return lastReview;
    }

    public void setLastReview(String lastReview) {
        this.lastReview = lastReview;
    }
}
