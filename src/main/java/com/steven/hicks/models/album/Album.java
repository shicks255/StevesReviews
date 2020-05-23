package com.steven.hicks.models.album;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.steven.hicks.models.Track;
import com.steven.hicks.models.artist.Artist;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    private String id = "";

    private String title = "";

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @JsonIgnore
    private Artist artist;

    @Column(name = "release_date")
    private String releaseDate = "";
    private String disambiguation = "";
    private String type = "";
    private String secondaryType = "";

    @OneToMany(mappedBy = "album")
    private List<Track> tracks;

    @OneToMany(mappedBy = "album")
    private List<AlbumImage> images;

    @Override
    public String toString() {
        return String.format("Album %s %s", title, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id.equals(album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(String secondaryType) {
        this.secondaryType = secondaryType;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<AlbumImage> getImages() {
        return images;
    }

    public void setImages(List<AlbumImage> images) {
        this.images = images;
    }
}