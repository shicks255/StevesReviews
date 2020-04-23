package com.steven.hicks.models.artist;

import com.steven.hicks.models.album.Album;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    private String id;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
//    @JsonManagedReference
    private List<Album> albums;

    private String name;
    private String disambiguation;
    private String area;
    private String country;
    private String beginDate;
    private String beginArea;
    private String beginAreaId;

    @Override
    public String toString() {
        return String.format("Artist %s %s", name, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id == artist.id;
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

    public String getBeginArea() {
        return beginArea;
    }

    public void setBeginArea(String beginArea) {
        this.beginArea = beginArea;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public void setDisambiguation(String disambiguation) {
        this.disambiguation = disambiguation;
    }

    public String getBeginAreaId() {
        return beginAreaId;
    }

    public void setBeginAreaId(String beginAreaId) {
        this.beginAreaId = beginAreaId;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

}