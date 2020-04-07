package com.steven.hicks.controllers;

import com.steven.hicks.beans.album.Image;
import com.steven.hicks.logic.AlbumSearcher;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService m_albumService;

    @GetMapping(value = "/{id}/{size}")
    public Image getAlbumArtwork(@PathVariable int id, @PathVariable(required = false) String size) {

        Album album = m_albumService.getById(id);
        AlbumSearcher albumSearcher = new AlbumSearcher();
        com.steven.hicks.beans.album.Album searchResult = albumSearcher.getFullAlbum(album.getMbid(), album.getName(), album.getArtist().getName());

        Image[] images = searchResult.getImage();

        Image imageUrl = Stream.of(images)
                .filter(x -> x.getSize().equalsIgnoreCase(size))
                .findFirst()
                .orElse(images[0]);

        return imageUrl;
    }

}
