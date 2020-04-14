package com.steven.hicks.models.dtos;

import com.steven.hicks.models.Review;
import com.steven.hicks.models.User;

import java.time.format.DateTimeFormatter;

public class ReviewDTO {

    private static DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private int id;
//    private Album album;
    private String albumId;
    private User user;
    private String addedOn;
    private String content;
    private float rating;
    private String colorClass;

    public ReviewDTO(Review review) {
        this.id = review.getId();
//        this.album = review.getAlbum();
        this.albumId = review.getAlbumId();
        this.user = review.getUser();
        this.content = review.getContent();
        this.rating = review.getRating();

        this.addedOn = FORMATTER.format(review.getAddedOn());
        if (this.rating >= 3.5)
            this.colorClass = "is-success";
        else if (this.rating >= 2.0)
            this.colorClass = "is-warning";
        else if (this.rating < 2.0)
            this.colorClass = "is-danger";
        else
            this.colorClass = "is-light";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    //    public Album getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(Album album) {
//        this.album = album;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getColorClass() {
        return colorClass;
    }

    public void setColorClass(String colorClass) {
        this.colorClass = colorClass;
    }
}
