package com.steven.hicks.models.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.steven.hicks.models.AuditClass;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "artist_images")
public class ArtistImage extends AuditClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @JsonIgnore
    private Artist artist;

    private String text;
    private String url;

    @Override
    public String toString() {
        return String.format("ArtistImage %d", id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistImage that = (ArtistImage) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
