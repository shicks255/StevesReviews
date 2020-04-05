package com.steven.hicks.models.album;

import com.steven.hicks.models.artist.Artist;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private String mbid;
    private String url;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private String summary;
    private String text;

}
