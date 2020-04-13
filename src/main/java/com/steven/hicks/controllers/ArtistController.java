package com.steven.hicks.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.logic.musicBrainz.MBArtistSearcher;
import com.steven.hicks.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    ArtistService m_artistService;

    @Value("${fanArtKey}")
    private String FAN_ART_KEY = "";

    private MBArtistSearcher m_mbArtistSearcher = new MBArtistSearcher();
    private MBAlbumSearcher m_mbAlbumSearcher = new MBAlbumSearcher();

    @GetMapping("/{id}")
    public JsonNode getArtist(@PathVariable("id") String id) {
//        return m_artistService.getArtistById(artistId);

        JsonNode artist =
                m_mbArtistSearcher.getArtist(id);

        return artist;
    }

    @GetMapping("/search/{searchTerms}")
    public JsonNode searchForArtist(@PathVariable String searchTerms) {
        JsonNode artistSearchResults = m_mbArtistSearcher.searchForArtist(searchTerms);
//        JsonNode artistSearchResults = m_mbArtistSearcher.searchForArtistWithImages(searchTerms, FAN_ART_KEY);
        System.out.println(artistSearchResults);
        return artistSearchResults;
    }

}
