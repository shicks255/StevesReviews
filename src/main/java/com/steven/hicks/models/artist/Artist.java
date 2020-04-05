package com.steven.hicks.models.artist;

import javax.persistence.*;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String summary;
    private String content;
    private String mbid;

}
