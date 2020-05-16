package com.steven.hicks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.models.User;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.dtos.AlbumReviewsArtist;
import com.steven.hicks.models.dtos.ReviewWithAlbumAndAverage;
import com.steven.hicks.services.AlbumService;
import com.steven.hicks.services.JwtTokenService;
import com.steven.hicks.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService m_albumService;
    @Autowired
    private ReviewService m_reviewService;
    @Autowired
    private JwtTokenService m_jwtTokenService;

    private ObjectMapper m_objectMapper = new ObjectMapper();
    private MBAlbumSearcher m_mbAlbumSearcher = new MBAlbumSearcher();

    @GetMapping("/artist/{artistMbid}")
    public List<Album> getAlbumsForArtist(@PathVariable String artistMbid) {
        List<Album> albums = m_albumService.getAlbumsByArtist(artistMbid);
        return albums;
    }

    @GetMapping("/{albumMbid}")
    public AlbumReviewsArtist getAlbum(@PathVariable String albumMbid, ServletRequest request) throws Exception {
        User user = null;
        try
        {
            user = m_jwtTokenService.getUserFromToken((HttpServletRequest) request);
        } catch (Exception e)
        {
        }
        return m_albumService.getAlbumReviewArtistDTO(albumMbid, user);
    }

    @GetMapping("/topRated")
    public List<ReviewWithAlbumAndAverage> getTopRated() throws Exception {
       return m_albumService.getTopRated();
    }
}
