package com.steven.hicks.models.dtos;

import java.time.LocalDateTime;
import java.util.Objects;

public class SiteStats {

    private long users;
    private long albums;
    private long reviews;
    private long ratings;
    private long fiveStars;
    private LocalDateTime lastFetched;
    
    public SiteStats(long users, long albums, long reviews, long ratings, long fiveStars) {
        this.users = users;
        this.albums = albums;
        this.reviews = reviews;
        this.ratings = ratings;
        this.fiveStars = fiveStars;
        this.lastFetched = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteStats siteStats = (SiteStats) o;
        return users == siteStats.users &&
                albums == siteStats.albums &&
                reviews == siteStats.reviews &&
                ratings == siteStats.ratings &&
                fiveStars == siteStats.fiveStars;
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, albums, reviews, ratings, fiveStars);
    }

    public long getUsers() {
        return users;
    }

    public void setUsers(long users) {
        this.users = users;
    }

    public long getAlbums() {
        return albums;
    }

    public void setAlbums(long albums) {
        this.albums = albums;
    }

    public long getReviews() {
        return reviews;
    }

    public void setReviews(long reviews) {
        this.reviews = reviews;
    }

    public long getRatings() {
        return ratings;
    }

    public void setRatings(long ratings) {
        this.ratings = ratings;
    }

    public long getFiveStars() {
        return fiveStars;
    }

    public void setFiveStars(long fiveStars) {
        this.fiveStars = fiveStars;
    }

    public LocalDateTime getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(LocalDateTime lastFetched) {
        this.lastFetched = lastFetched;
    }
}
