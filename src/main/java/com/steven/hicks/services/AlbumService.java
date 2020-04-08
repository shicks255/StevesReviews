package com.steven.hicks.services;

import com.steven.hicks.beans.album.Image;
import com.steven.hicks.logic.AlbumSearcher;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.dtos.AlbumWithReviewAverageDTO;
import com.steven.hicks.repositories.AlbumRepository;
import com.steven.hicks.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository m_albumRepository;

    @Autowired
    ReviewRepository m_reviewRepository;

    @Autowired
    JdbcTemplate m_jdbcTemplate;

    public Album getById(int id) {
        return m_albumRepository.getOne(id);
    }

    public List<AlbumWithReviewAverageDTO> getTopRated() {
        List<String[]> albums = m_jdbcTemplate.query("select a.id, avg(b.rating) from albums a join reviews b on a.id = b.album_id group by a.id order by avg desc;",
                (rs, num) -> {
                    String[] albumIdAndAvgRating = new String[2];
                    albumIdAndAvgRating[0] = rs.getInt("id") + "";
                    albumIdAndAvgRating[1] = rs.getDouble("avg") + "";
                    return albumIdAndAvgRating;
                });

        List<AlbumWithReviewAverageDTO> albumWithReviewAverageDTOS = new ArrayList<>();

        for (String[] a : albums) {
            List<Review> reviews = m_reviewRepository.findAllByAlbumId(Integer.parseInt(a[0]));
            Review top = reviews.stream().sorted(Comparator.comparing(Review::getRating))
                    .findFirst().orElseThrow();
            Album album = m_albumRepository.getOne(Integer.parseInt(a[0]));

            AlbumSearcher albumSearcher = new AlbumSearcher();
            com.steven.hicks.beans.album.Album searchResult = albumSearcher.getFullAlbum(album.getMbid(), album.getName(), album.getArtist().getName());

            Image[] images = searchResult.getImage();

            Image imageUrl = Stream.of(images)
                    .filter(x -> x.getSize().equalsIgnoreCase("large"))
                    .findFirst()
                    .orElse(images[0]);

            albumWithReviewAverageDTOS.add(
                    new AlbumWithReviewAverageDTO(album, top, imageUrl.getText(), Double.parseDouble(a[1]))
            );
        }

        return albumWithReviewAverageDTOS;
    }

}
