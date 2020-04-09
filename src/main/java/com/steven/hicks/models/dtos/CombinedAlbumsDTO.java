package com.steven.hicks.models.dtos;

import com.steven.hicks.beans.ArtistAlbums;

import java.util.List;

public class CombinedAlbumsDTO {

    private List<AlbumWithImageDTO> m_albumWithImageDTOList;
    private List<ArtistAlbums> m_artistAlbums;

    public CombinedAlbumsDTO(List<AlbumWithImageDTO> albumWithImageDTOS, List<ArtistAlbums> artistAlbums) {
        this.m_albumWithImageDTOList = albumWithImageDTOS;
        this.m_artistAlbums = artistAlbums;
    }

    public List<AlbumWithImageDTO> getAlbumWithImageDTOList() {
        return m_albumWithImageDTOList;
    }

    public void setAlbumWithImageDTOList(List<AlbumWithImageDTO> albumWithImageDTOList) {
        m_albumWithImageDTOList = albumWithImageDTOList;
    }

    public List<ArtistAlbums> getArtistAlbums() {
        return m_artistAlbums;
    }

    public void setArtistAlbums(List<ArtistAlbums> artistAlbums) {
        m_artistAlbums = artistAlbums;
    }
}
