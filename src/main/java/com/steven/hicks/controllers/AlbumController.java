package com.steven.hicks.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.models.dtos.AlbumWithReviewDTO;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.services.AlbumService;
import com.steven.hicks.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService m_albumService;
    @Autowired
    private ReviewService m_reviewService;

    private ObjectMapper m_objectMapper = new ObjectMapper();
    private MBAlbumSearcher m_mbAlbumSearcher = new MBAlbumSearcher();

    @GetMapping("/artist/{artistMbid}")
    public JsonNode getAlbumsForArtist(@PathVariable String artistMbid) {
        JsonNode albums = m_mbAlbumSearcher.searchForAlbumsByArtist(artistMbid);

        for (Iterator<JsonNode> it = albums.iterator(); it.hasNext(); ) {
            JsonNode album = it.next();
            Double rating = m_reviewService.getAverageRating(album.get("id").asText());
            ((ObjectNode)album).put("rating", rating);
        }

        return albums;
    }

    @GetMapping("/{albumMbid}")
    public JsonNode getAlbum(@PathVariable String albumMbid) {
        JsonNode album = m_mbAlbumSearcher.getAlbum(albumMbid);
        List<ReviewDTO> reviews = m_reviewService.getReviewsForAlbum(albumMbid);
        try
        {
            Double rating = m_reviewService.getAverageRating(albumMbid);
            ((ObjectNode)album).put("rating", rating);
            String reviewNode = m_objectMapper.writeValueAsString(reviews);
            JsonNode review = m_objectMapper.readTree(reviewNode);
            ((ObjectNode)album).put("reviews", review);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return album;
    }

    @GetMapping("/topRated")
    public List<AlbumWithReviewDTO> getTopRated() {
       return m_albumService.getTopRated();
    }
}
