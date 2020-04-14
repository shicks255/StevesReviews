package com.steven.hicks.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.steven.hicks.beans.album.Image;
import com.steven.hicks.logic.AlbumSearcher;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.dtos.AlbumWithReviewAverageDTO;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.services.AlbumService;
import com.steven.hicks.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

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

        return albums;
    }

    @GetMapping("/{albumMbid}")
    public JsonNode getAlbumForArtist(@PathVariable String albumMbid) {
        JsonNode album = m_mbAlbumSearcher.getAlbum(albumMbid);

        List<ReviewDTO> reviews = m_reviewService.getReviewsForAlbum(albumMbid);
        try
        {
            String reviewNode = m_objectMapper.writeValueAsString(reviews);
            JsonNode review = m_objectMapper.readTree(reviewNode);
            ((ObjectNode)album).put("reviews", review);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return album;
    }

    @GetMapping(value = "/{id}/{size}")
    public Image getAlbumArtwork(@PathVariable int id, @PathVariable(required = false) String size) {

        Album album = m_albumService.getById(id);
        AlbumSearcher albumSearcher = new AlbumSearcher("c349ab1fcb6b132ffb8d842e982458db");
        com.steven.hicks.beans.album.Album searchResult = albumSearcher.getFullAlbum(album.getMbid(), album.getName(), album.getArtist().getName());

        Image[] images = searchResult.getImage();

        Image imageUrl = Stream.of(images)
                .filter(x -> x.getSize().equalsIgnoreCase(size))
                .findFirst()
                .orElse(images[0]);

        return imageUrl;
    }

    @GetMapping("/topRated")
    public List<AlbumWithReviewAverageDTO> getTopRated() {
       return m_albumService.getTopRated();
    }

//    @GetMapping("/artist/{artistId}")
//    public CombinedAlbumsDTO getAlbumsForArtist(@PathVariable("artistId") int artistId) {
//        List<AlbumWithImageDTO> albumWithImageDTOS = m_albumService.getAlbumsForArtist(artistId);
//        List<ArtistAlbums> nonDBAlbums = m_albumService.getNonDBAlbums(artistId);
//
//        List<String> dbAlbumNames = albumWithImageDTOS.stream()
//                .map(x -> x.getAlbum().getName())
//                .collect(Collectors.toList());
//
//        List<ArtistAlbums> filteredNonDBAlbums = nonDBAlbums.stream()
//                .filter(s -> !dbAlbumNames.contains(s.getName()))
//                .collect(Collectors.toList());
//
//        return new CombinedAlbumsDTO(albumWithImageDTOS, filteredNonDBAlbums);
//    }

}
