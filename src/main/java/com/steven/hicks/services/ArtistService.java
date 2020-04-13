package com.steven.hicks.services;

import com.steven.hicks.beans.artist.Image;
import com.steven.hicks.logic.ArtistQueryBuilder;
import com.steven.hicks.logic.ArtistSearcher;
import com.steven.hicks.models.album.AlbumImage;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.models.artist.ArtistImage;
import com.steven.hicks.models.dtos.ArtistWithImageDTO;
import com.steven.hicks.repositories.ArtistImageRepository;
import com.steven.hicks.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    @Autowired
    ArtistRepository m_artistRepository;

    @Autowired
    ArtistImageRepository m_artistImageRepository;

//    public ArtistWithImageDTO getArtistById(int id) {
//        Artist artist = m_artistRepository.findById(id)
//                .orElseThrow();
//
//        ArtistQueryBuilder aqb = new ArtistQueryBuilder.Builder()
//                .artistName(artist.getName())
//                .mbid(artist.getMbid())
//                .build();
//
//        ArtistSearcher searcher = new ArtistSearcher();
//        List<com.steven.hicks.beans.artist.Artist> searchResults = searcher.searchForArtists(aqb);
//        if (artist.getMbid().length() > 0) {
//            com.steven.hicks.beans.artist.Artist full = searcher.getfusearchResults.stream()
//                    .filter(x -> x.getMbid().equalsIgnoreCase(artist.getMbid()))
//                    .findFirst().orElseThrow()
//                    .getImage();
//
//            return new ArtistWithImageDTO(artist, images[0].getText());
//        }
//
//        Image[] images = searchResults.stream()
//                .filter(x -> x.getName().equalsIgnoreCase(artist.getName()))
//                .findFirst().orElseThrow()
//                .getImage();
//
//        return new ArtistWithImageDTO(artist, images[0].getSize());
//    }
}
