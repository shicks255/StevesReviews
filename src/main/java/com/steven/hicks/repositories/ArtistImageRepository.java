package com.steven.hicks.repositories;

import com.steven.hicks.models.artist.ArtistImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistImageRepository extends JpaRepository<ArtistImage, Integer> {

    List<ArtistImage> findAllById(int artistId);
    boolean existsAllByUrl(String url);
}
