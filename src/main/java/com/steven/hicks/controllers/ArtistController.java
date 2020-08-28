package com.steven.hicks.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.steven.hicks.aspects.Logged;
import com.steven.hicks.logic.musicBrainz.MBArtistSearcher;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.services.ArtistMetricsService;
import com.steven.hicks.services.ArtistService;
import com.steven.hicks.services.SearchMetricsService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    @Autowired
    ArtistService m_artistService;
    @Autowired
    ArtistMetricsService m_artistMetricsService;
    @Autowired
    SearchMetricsService m_searchMetricsService;

    private MBArtistSearcher m_mbArtistSearcher = new MBArtistSearcher();

    @GetMapping("/{id}")
    @Logged
    @Timed()
    public MappingJacksonValue getArtist(@PathVariable("id") String id) {

        SimpleBeanPropertyFilter albumFilter = SimpleBeanPropertyFilter.serializeAllExcept("tracks");
        SimpleBeanPropertyFilter artistFilter = SimpleBeanPropertyFilter.serializeAllExcept("");
        SimpleBeanPropertyFilter reviewFilter = SimpleBeanPropertyFilter.serializeAllExcept("artist", "album");

        Artist artist = m_artistService.getArtist(id);
        artist.getImages();
        m_artistMetricsService.upsertArtistMetrics(id, artist.getName());
        FilterProvider filters =
                new SimpleFilterProvider().addFilter("albumFilter", albumFilter)
                        .addFilter("artistFilter", artistFilter)
                        .addFilter("reviewFilter", reviewFilter);
        MappingJacksonValue mapping = new MappingJacksonValue(artist);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/search/{searchTerms}")
    @Logged
    @Timed()
    public JsonNode searchForArtist(@PathVariable String searchTerms) {

        m_searchMetricsService.upsert(searchTerms);
        JsonNode artistSearchResults = m_mbArtistSearcher.searchForArtist(searchTerms);
        return artistSearchResults;
    }
}
