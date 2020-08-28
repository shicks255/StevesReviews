package com.steven.hicks.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.steven.hicks.aspects.Logged;
import com.steven.hicks.models.User;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.dtos.AlbumArtistReviewsDTO;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.services.AlbumMetricsService;
import com.steven.hicks.services.AlbumService;
import com.steven.hicks.services.JwtTokenService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService m_albumService;
    @Autowired
    private AlbumMetricsService m_albumMetricsService;
    @Autowired
    private JwtTokenService m_jwtTokenService;

    @GetMapping("/artist/{artistMbid}")
    @Timed()
    @Logged
    public MappingJacksonValue getAlbumsForArtist(@PathVariable String artistMbid) {

        SimpleBeanPropertyFilter albumFilter = SimpleBeanPropertyFilter.serializeAllExcept("tracks");
        SimpleBeanPropertyFilter artistFilter = SimpleBeanPropertyFilter.serializeAllExcept("albums");
        SimpleBeanPropertyFilter reviewFilter = SimpleBeanPropertyFilter.serializeAllExcept("artist", "album");

        List<Album> albums = m_albumService.getAlbumsByArtist(artistMbid);
        FilterProvider filters =
                new SimpleFilterProvider().addFilter("albumFilter", albumFilter)
                        .addFilter("artistFilter", artistFilter)
                        .addFilter("reviewFilter", reviewFilter);
        MappingJacksonValue mapping = new MappingJacksonValue(albums);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/{albumMbid}")
    @Timed()
    @Logged
    public MappingJacksonValue getAlbum(@PathVariable String albumMbid, ServletRequest request) throws Exception {
        User user = null;
        try {
            user = m_jwtTokenService.getUserFromToken((HttpServletRequest) request);
        } catch (Exception e) {
        }

        SimpleBeanPropertyFilter albumFilter = SimpleBeanPropertyFilter.serializeAllExcept("albums");
        SimpleBeanPropertyFilter artistFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        SimpleBeanPropertyFilter reviewFilter = SimpleBeanPropertyFilter.serializeAllExcept("");

        AlbumArtistReviewsDTO dto = m_albumService.getAlbumReviewArtistDTO(albumMbid, user);
        m_albumMetricsService.upsertAlbumMetrics(albumMbid, dto.getAlbum().getTitle());
        FilterProvider filters =
                new SimpleFilterProvider().addFilter("albumFilter", albumFilter)
                        .addFilter("artistFilter", artistFilter)
                        .addFilter("reviewFilter", reviewFilter);
        MappingJacksonValue mapping = new MappingJacksonValue(dto);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/topRated")
    @Timed()
    @Logged
    public MappingJacksonValue getTopRated() {

        SimpleBeanPropertyFilter albumFilter = SimpleBeanPropertyFilter.serializeAllExcept("tracks");
        SimpleBeanPropertyFilter artistFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id");
        SimpleBeanPropertyFilter reviewFilter = SimpleBeanPropertyFilter.serializeAll();

        List<ReviewDTO> reviews = m_albumService.getTopRated();
        FilterProvider filters =
                new SimpleFilterProvider().addFilter("albumFilter", albumFilter)
                        .addFilter("artistFilter", artistFilter)
                        .addFilter("reviewFilter", reviewFilter);
        MappingJacksonValue mapping = new MappingJacksonValue(reviews);
        mapping.setFilters(filters);
        return mapping;
    }
}
