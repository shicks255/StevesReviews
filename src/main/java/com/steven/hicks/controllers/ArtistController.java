package com.steven.hicks.controllers;

import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.models.dtos.ArtistWithImageDTO;
import com.steven.hicks.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    ArtistService m_artistService;

    @GetMapping("/{id}")
    public ArtistWithImageDTO getArtist(@PathVariable("id") int artistId) {
        return m_artistService.getArtistById(artistId);
    }

}
