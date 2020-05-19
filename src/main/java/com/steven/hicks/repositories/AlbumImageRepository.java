package com.steven.hicks.repositories;

import com.steven.hicks.models.album.AlbumImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumImageRepository extends JpaRepository<AlbumImage, String> {

}
