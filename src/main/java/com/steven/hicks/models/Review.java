package com.steven.hicks.models;

import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.artist.Artist;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "added_on")
    private LocalDateTime addedOn;

    private float rating;
}
