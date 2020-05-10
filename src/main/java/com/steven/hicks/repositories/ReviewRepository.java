package com.steven.hicks.repositories;

import com.steven.hicks.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByAlbumId(String albumId);
    Review findFirstByAlbumIdOrderByRatingDesc(String albumId);
    List<Review> findAllByUserId(int userId);

    @Query(value = "select count(*) from reviews where user_id = ?1", nativeQuery = true)
    int getCountOfReviewsByUserId(int userId);

    @Query(value = "select AVG(rating) from reviews where album_id = ?1", nativeQuery = true)
    Double getAverageRating(String albumId);
}
