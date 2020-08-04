package com.steven.hicks.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.steven.hicks.logic.musicBrainz.MBArtistSearcher;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.services.ArtistService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    @Autowired
    ArtistService m_artistService;

    @Autowired
    MeterRegistry m_meterRegistry;

    private MBArtistSearcher m_mbArtistSearcher = new MBArtistSearcher();

    @GetMapping("/{id}")
    public Artist getArtist(@PathVariable("id") String id) {
        Artist artist = m_artistService.getArtist(id);
        artist.getImages();

        m_meterRegistry.counter("artistHits",
                Arrays.asList(
                        Tag.of("artist", artist.getName())
                )
        ).increment();

        return artist;
    }

    @GetMapping("/search/{searchTerms}")
    public JsonNode searchForArtist(@PathVariable String searchTerms) {

        m_meterRegistry.counter("artistSearch",
                Arrays.asList(
                        Tag.of("searchTerms", searchTerms)
                )
        ).increment();

        JsonNode artistSearchResults = m_mbArtistSearcher.searchForArtist(searchTerms);
        return artistSearchResults;
    }

}
