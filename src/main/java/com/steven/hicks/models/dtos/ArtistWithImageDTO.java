package com.steven.hicks.models.dtos;

import com.steven.hicks.models.artist.Artist;

public class ArtistWithImageDTO {
    private Artist m_artist;
    private String imageUrl;

    public ArtistWithImageDTO(Artist artist, String image) {
        this.m_artist = artist;
        this.imageUrl = image;
    }

    public Artist getArtist() {
        return m_artist;
    }

    public void setArtist(Artist artist) {
        m_artist = artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
