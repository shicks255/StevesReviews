package com.steven.hicks.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.dtos.AlbumWithReviewDTO;
import com.steven.hicks.repositories.AlbumRepository;
import com.steven.hicks.repositories.ArtistRepository;
import com.steven.hicks.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository m_albumRepository;

    @Autowired
    ReviewRepository m_reviewRepository;

    @Autowired
    ArtistRepository m_artistRepository;

    private MBAlbumSearcher m_mbAlbumSearcher = new MBAlbumSearcher();

    @Autowired
    JdbcTemplate m_jdbcTemplate;

    public Album getById(int id) {
        return m_albumRepository.getOne(id);
    }

    public List<AlbumWithReviewDTO> getTopRated() {
        List<AlbumWithReviewDTO> albumWithReviews = m_jdbcTemplate.query("select album_id,avg(rating)as avrg from reviews group by album_id order by avrg desc limit 10;",
                (rs, num) -> {
                    String albumId = rs.getString("album_id");
                    double rating = rs.getDouble("avrg");
                    Review review = m_reviewRepository.findFirstByAlbumIdOrderByRatingDesc(albumId);
                    JsonNode album = m_mbAlbumSearcher.getAlbum(albumId);
                    try
                    {
                        Thread.sleep(750);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    return new AlbumWithReviewDTO(album, rating, review);
                });

        return albumWithReviews;
    }
}
