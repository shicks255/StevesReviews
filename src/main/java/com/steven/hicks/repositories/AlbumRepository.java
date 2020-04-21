package com.steven.hicks.repositories;

import com.steven.hicks.models.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, String> {
    List<Album> findAllByArtistId(String artistId);
}
