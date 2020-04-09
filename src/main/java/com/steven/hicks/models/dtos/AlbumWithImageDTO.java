package com.steven.hicks.models.dtos;

import com.steven.hicks.models.album.Album;

public class AlbumWithImageDTO {

    private Album m_album;
    private String imageUrl;

    public AlbumWithImageDTO(Album album, String imageUrl) {
        this.m_album = album;
        this.imageUrl = imageUrl;
    }

    public Album getAlbum() {
        return m_album;
    }

    public void setAlbum(Album album) {
        m_album = album;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
