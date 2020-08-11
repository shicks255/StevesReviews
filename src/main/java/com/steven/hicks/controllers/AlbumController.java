package com.steven.hicks.controllers;

import com.steven.hicks.models.User;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.dtos.AlbumArtistReviewsDTO;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.services.AlbumService;
import com.steven.hicks.services.JwtTokenService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService m_albumService;
    @Autowired
    private JwtTokenService m_jwtTokenService;
    @Autowired
    private MeterRegistry m_meterRegistry;

    @GetMapping("/artist/{artistMbid}")
    @Timed()
    public List<Album> getAlbumsForArtist(@PathVariable String artistMbid) {
        List<Album> albums = m_albumService.getAlbumsByArtist(artistMbid);
        return albums;
    }

    @GetMapping("/{albumMbid}")
    @Timed()
    public AlbumArtistReviewsDTO getAlbum(@PathVariable String albumMbid, ServletRequest request) throws Exception {
        User user = null;
        try {
            user = m_jwtTokenService.getUserFromToken((HttpServletRequest) request);
        } catch (Exception e) {
        }

        AlbumArtistReviewsDTO dto = m_albumService.getAlbumReviewArtistDTO(albumMbid, user);
        m_meterRegistry.counter("albumHits",
                Arrays.asList(
                        Tag.of("artist", dto.getArtist().getName()),
                        Tag.of("album", dto.getAlbum().getTitle())
                )
        ).increment();


        return dto;
    }

    @GetMapping("/topRated")
    @Timed
    public List<ReviewDTO> getTopRated() {
        return m_albumService.getTopRated();
    }
}
