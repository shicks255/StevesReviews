package com.steven.hicks.repositories;

import com.steven.hicks.models.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, String> {
    @Query(value = "select * from albums where artist_id = ?1 order by release_date asc nulls last", nativeQuery = true)
    List<Album> findAllByArtistIdOrderByReleaseDate(String artistId);
}
