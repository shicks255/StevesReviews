package com.steven.hicks.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.Track;
import com.steven.hicks.models.User;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.models.dtos.AlbumReviewsArtist;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.models.dtos.ReviewWithAlbum;
import com.steven.hicks.models.dtos.ReviewWithAlbumAndAverage;
import com.steven.hicks.repositories.AlbumRepository;
import com.steven.hicks.repositories.ArtistRepository;
import com.steven.hicks.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    @Autowired
    ObjectMapper m_objectMapper;

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
        return m_albumRepository.findAllByArtistIdOrderByReleaseDate(artistId);
    }

    public AlbumReviewsArtist getAlbumReviewArtistDTO(String albumId, User user) {
        Album album = getById(albumId);
        Artist artist = album.getArtist();
        List<ReviewDTO> reviews = m_reviewService.getReviewsForAlbum(albumId);
        Double rating = m_reviewService.getAverageRating(albumId);

        if (user != null) {
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

    public List<ReviewWithAlbumAndAverage> getTopRated() throws Exception {
        List<ReviewWithAlbumAndAverage> albumWithReviews = m_jdbcTemplate.query("select album_id,avg(rating)as avrg from reviews group by album_id order by avrg desc limit 10;",
                (rs, num) -> {
                    String albumId = rs.getString("album_id");
                    double rating = rs.getDouble("avrg");
                    Review review = m_reviewService.findFirstByAlbumIdOrderByRatingDesc(albumId);
                    Album album = getById(albumId);
                    try
                    {
                        String json = m_objectMapper.writeValueAsString(album);
                        JsonNode node = m_objectMapper.readTree(json);
                        JsonNode rev = m_objectMapper.readTree(m_objectMapper.writeValueAsString(review));
                        JsonNode art = m_objectMapper.readTree(m_objectMapper.writeValueAsString(album.getArtist()));

                        ReviewWithAlbum rwa = new ReviewWithAlbum(node, rev, art);
                        return new ReviewWithAlbumAndAverage(rwa, rating);
                    } catch (JsonProcessingException e)
                    {
                        e.printStackTrace();
                        return null;
                    }
                });

        return albumWithReviews;
    }
}
