package com.steven.hicks.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.Track;
import com.steven.hicks.models.User;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.models.dtos.AlbumReviewsArtist;
import com.steven.hicks.models.dtos.AlbumWithReviewDTO;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.repositories.AlbumRepository;
import com.steven.hicks.repositories.ArtistRepository;
import com.steven.hicks.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository m_albumRepository;
    @Autowired
    TrackRepository m_trackRepository;
    @Autowired
    ReviewService m_reviewService;
    @Autowired
    ArtistRepository m_artistRepository;
    @Autowired
    JdbcTemplate m_jdbcTemplate;

    private MBAlbumSearcher m_mbAlbumSearcher = new MBAlbumSearcher();

    public Album getById(String id) {
        Album album = m_albumRepository.findById(id).orElseThrow();

        if (album.getTracks() == null || album.getTracks().isEmpty()) {
            JsonNode releases = m_mbAlbumSearcher.getReleasesByReleaseGroup(id);
            String releaseId = "";
            String oldest = "3000-12-31";

            for (Iterator<JsonNode> it = releases.iterator(); it.hasNext(); ) {
                JsonNode release = it.next();

                String releaseDate = release.has("date") ? release.get("date").asText() : "3000-12-31";
                if (releaseDate.length() < 5)
                    releaseDate += "-12-31";
                if (oldest.equals("3000") || releaseDate.compareTo(oldest) < 0) {
                    oldest = releaseDate;
                    releaseId = release.get("id").asText();
                }
            }

            Map<Integer, JsonNode> tracksMap = m_mbAlbumSearcher.getTrackz(releaseId);
            if (tracksMap != null) {
                List<Track> trackList = new ArrayList<>();
                for (Map.Entry<Integer, JsonNode> es : tracksMap.entrySet()) {
                    JsonNode tracks = es.getValue();
                    for (Iterator<JsonNode> iterator = tracks.iterator(); iterator.hasNext(); ) {
                        JsonNode track = iterator.next();

                        Track tracksToAdd = new Track();
                        tracksToAdd.setAlbum(album);
                        tracksToAdd.setLength(track.get("length").asLong());
                        tracksToAdd.setNumber(track.get("number").asText());
                        tracksToAdd.setPosition(track.get("position").asInt());
                        tracksToAdd.setTitle(track.get("title").asText());
                        tracksToAdd.setDisc(es.getKey());
                        trackList.add(tracksToAdd);
                    }

                    m_trackRepository.saveAll(trackList);
                    album.setTracks(trackList);
                }
            }

            System.out.println(tracksMap);
        }

        return album;
    }

    public List<Album> getAlbumsByArtist(String artistId) {
        return m_albumRepository.findAllByArtistId(artistId);
    }

    public AlbumReviewsArtist getAlbumReviewArtistDTO(String albumId) {
        Album album = getById(albumId);
        Artist artist = album.getArtist();
        List<ReviewDTO> reviews = m_reviewService.getReviewsForAlbum(albumId);
        Double rating = m_reviewService.getAverageRating(albumId);

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ReviewDTO userReview = reviews.stream()
                    .filter(x -> x.getUser().equals(user))
                    .findFirst()
                    .orElse(null);
            if (userReview != null)
                reviews.removeIf(x -> x.getId() == userReview.getId());
            return new AlbumReviewsArtist(artist, reviews, rating, album, userReview);
        }

        return new AlbumReviewsArtist(artist, reviews, rating, album, null);
    }

    public List<AlbumWithReviewDTO> getTopRated() {
        List<AlbumWithReviewDTO> albumWithReviews = m_jdbcTemplate.query("select album_id,avg(rating)as avrg from reviews group by album_id order by avrg desc limit 10;",
                (rs, num) -> {
                    String albumId = rs.getString("album_id");
                    double rating = rs.getDouble("avrg");
                    Review review = m_reviewService.findFirstByAlbumIdOrderByRatingDesc(albumId);
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
