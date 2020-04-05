package com.steven.hicks.models;

import com.steven.hicks.models.album.Album;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tracks")
public class Track implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Id
    private BigDecimal rank;

    private String name;
    private BigDecimal duration;
}
