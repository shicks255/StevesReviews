package com.steven.hicks.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.steven.hicks.logic.musicBrainz.MBArtistSearcher;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    @Autowired
    ArtistService m_artistService;

    private MBArtistSearcher m_mbArtistSearcher = new MBArtistSearcher();

    @GetMapping("/{id}")
    public Artist getArtist(@PathVariable("id") String id) {
        Artist artist = m_artistService.getArtist(id);
        artist.getImages();
        return artist;
    }

    @GetMapping("/search/{searchTerms}")
    public JsonNode searchForArtist(@PathVariable String searchTerms) {
        JsonNode artistSearchResults = m_mbArtistSearcher.searchForArtist(searchTerms);
        return artistSearchResults;
    }

}
