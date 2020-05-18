package com.steven.hicks.models.dtos;

public class UserStats {

    private int reviews;
    private long fiveStarReviews;
    private float averageRating;
    private int averageReviewLength;
    private String lastReview;

    public UserStats(int reviews, long fiveStarReviews, float averageRating, int averageReviewLength, String lastReview) {
        this.reviews = reviews;
        this.fiveStarReviews = fiveStarReviews;
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

    public long getFiveStarReviews() {
        return fiveStarReviews;
    }

    public void setFiveStarReviews(long fiveStarReviews) {
        this.fiveStarReviews = fiveStarReviews;
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
