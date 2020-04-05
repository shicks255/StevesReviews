package com.steven.hicks.repositories;

import com.steven.hicks.models.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

}
