package com.steven.hicks.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.logic.musicBrainz.MBArtistSearcher;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.album.AlbumImage;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.models.artist.ArtistImage;
import com.steven.hicks.repositories.AlbumImageRepository;
import com.steven.hicks.repositories.AlbumRepository;
import com.steven.hicks.repositories.ArtistImageRepository;
import com.steven.hicks.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArtistService {

    @Autowired
    ArtistRepository m_artistRepository;
    @Autowired
    ArtistImageRepository m_artistImageRepository;
    @Autowired
    AlbumImageRepository m_albumImageRepository;
    @Autowired
    AlbumRepository m_albumRepository;

    @Value("${fanArtKey}")
    private String FAN_ART_KEY = "";

    private MBArtistSearcher m_mbArtistSearcher = new MBArtistSearcher();
    private MBAlbumSearcher m_mbAlbumSearcher = new MBAlbumSearcher();

    public List<Artist> getAllArtists() {
        return m_artistRepository.findAll();
    }

    public Artist getArtist(String mbid) {

        Artist artist = m_artistRepository.findById(mbid).orElseGet(() -> {
            JsonNode artistWithImages = m_mbArtistSearcher.getArtistWithImages(mbid, FAN_ART_KEY);
            System.out.println(artistWithImages);
            String area = artistWithImages.get("area").get("name").asText();
            String beginArea = artistWithImages.get("begin_area").isEmpty() ? "" : artistWithImages.get("begin_area").get("name").asText();
            String beginAreaId = artistWithImages.get("begin-area").isEmpty() ? "" : artistWithImages.get("begin_area").get("id").asText();
            String beginDate = artistWithImages.get("life-span").get("begin").asText();
            String country = artistWithImages.get("country").asText();
            String dis = artistWithImages.get("disambiguation").asText();
            String id = artistWithImages.get("id").asText();
            String name = artistWithImages.get("name").asText();

            Artist newArtist = new Artist();
            newArtist.setArea(area);
            newArtist.setBeginArea(beginArea);
            newArtist.setBeginAreaId(beginAreaId);
            newArtist.setBeginDate(beginDate);
            newArtist.setCountry(country);
            newArtist.setDisambiguation(dis);
            newArtist.setId(id);
            newArtist.setName(name);

            m_artistRepository.save(newArtist);

            JsonNode images = artistWithImages.get("images");
            for (Iterator<JsonNode> iterator = images.iterator(); iterator.hasNext(); ) {
                ArtistImage image = new ArtistImage();
                JsonNode img = iterator.next();
                String t = img.findValue("#text").asText();

                image.setUrl(t);
                image.setArtist(newArtist);
                m_artistImageRepository.save(image);
            }

            JsonNode releaseDates = m_mbAlbumSearcher.searchForAlbumsByArtist(id, 0);
            Map<String,String> releaseToDate = new HashMap<>();
            for (Iterator<JsonNode> iterator = releaseDates.iterator(); iterator.hasNext(); ) {
                JsonNode thing = iterator.next();
                String idd = thing.get("id").asText();
                String date = thing.get("first-release-date").asText();
                releaseToDate.put(idd, date);
            }

            //also need to create the albums
            JsonNode albums = m_mbAlbumSearcher.getAlbumsForArtist(id, 0);
            List<Album> albumSet = new ArrayList<>();
            for (Iterator<JsonNode> it = albums.iterator(); it.hasNext(); ) {
                JsonNode release = it.next();

                String secondayType = release.get("secondary-types") != null ? release.get("secondary-types").textValue() : "";
                String primaryType = release.get("primary-type") != null ? release.get("primary-type").textValue() : null;
                if (primaryType == null)
                    continue;
                String albumId = release.get("id").asText();
                String releaseDate = releaseToDate.getOrDefault(albumId, "");
                String title = release.get("title").asText();
                Album album = new Album();
                album.setSecondaryType(secondayType);
                album.setType(primaryType);
                album.setReleaseDate(null);
                album.setReleaseDate(releaseDate);
                album.setTitle(title);
                album.setId(albumId);
                album.setArtist(newArtist);
                albumSet.add(album);
            }

            albumSet.removeIf(x -> !x.getType().equalsIgnoreCase("Album") && !x.getType().equalsIgnoreCase("Ep"));
            m_albumRepository.saveAll(albumSet);

            return getArtist(mbid);
        });

        return artist;
    }
}
