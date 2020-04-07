package com.steven.hicks.repositories;

import com.steven.hicks.models.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

}
