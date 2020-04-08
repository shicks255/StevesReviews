package com.steven.hicks.repositories;

import com.steven.hicks.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByAlbumId(int albumId);
}
