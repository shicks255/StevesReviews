package com.steven.hicks.models.artist;

import javax.persistence.*;

@Entity
@Table(name = "artist_images")
public class ArtistImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artist_id")
    private int id;

    private String text;
    private String url;
}
