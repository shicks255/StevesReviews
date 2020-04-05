package com.steven.hicks.models.album;

import javax.persistence.*;

@Entity
@Table(name = "album_images")
public class AlbumImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "album_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "album_id")
    private Album album;

    private String text;
    private String url;

}
