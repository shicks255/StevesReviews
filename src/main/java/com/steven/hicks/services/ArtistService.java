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
    private ObjectMapper m_objectMapper = new ObjectMapper();

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
            List<AlbumImage> albumImages = new ArrayList<>();
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

                JsonNode albumImagesa = m_mbAlbumSearcher.getReleaseGroupArt(albumId);
                for (Iterator<JsonNode> itt = albumImagesa.iterator(); itt.hasNext(); )
                {
                    JsonNode x = itt.next();
                    String text = "";
                    if (x.has("front") && x.get("front").asText().equals("true"))
                        text = "front";

                    if (x.has("back") && x.get("back").asText().equals("true"))
                        text = "back";

                    AlbumImage main = new AlbumImage();
                    main.setAlbum(album);
                    main.setSize("main");
                    main.setText(text);
                    main.setUrl(x.get("image").asText());
                    albumImages.add(main);

                    //now do thumbnail images
                    if (x.has("thumbnails"))
                    {
                        JsonNode thumbnails = x.get("thumbnails");
                        if (thumbnails.has("250")) {
                            AlbumImage thum_250 = new AlbumImage();
                            thum_250.setSize("250");
                            thum_250.setAlbum(album);
                            thum_250.setText(text);
                            thum_250.setUrl(thumbnails.get("250").asText());
                            albumImages.add(thum_250);
                        }
                        if (thumbnails.has("500")) {
                            AlbumImage thumb_500 = new AlbumImage();
                            thumb_500.setSize("500");
                            thumb_500.setAlbum(album);
                            thumb_500.setText(text);
                            thumb_500.setUrl(thumbnails.get("500").asText());
                            albumImages.add(thumb_500);
                        }
                        if (thumbnails.has("1200")) {
                            AlbumImage thum_1200 = new AlbumImage();
                            thum_1200.setSize("1200");
                            thum_1200.setAlbum(album);
                            thum_1200.setText(text);
                            thum_1200.setUrl(thumbnails.get("1200").asText());
                            albumImages.add(thum_1200);
                        }
                        if (thumbnails.has("large")) {
                            AlbumImage thum_large = new AlbumImage();
                            thum_large.setSize("large");
                            thum_large.setAlbum(album);
                            thum_large.setText(text);
                            thum_large.setUrl(thumbnails.get("large").asText());
                            albumImages.add(thum_large);
                        }
                        if (thumbnails.has(("small"))) {
                            AlbumImage thumb_small = new AlbumImage();
                            thumb_small.setSize("small");
                            thumb_small.setAlbum(album);
                            thumb_small.setText(text);
                            thumb_small.setUrl(thumbnails.get("small").asText());
                            albumImages.add(thumb_small);
                        }
                    }
                }
                album.setImages(albumImages);
                albumSet.add(album);
            }

            albumSet.removeIf(x -> !x.getType().equalsIgnoreCase("Album") && !x.getType().equalsIgnoreCase("Ep"));
            m_albumRepository.saveAll(albumSet);
            m_albumImageRepository.saveAll(albumImages);

            return newArtist;
        });

        return artist;
    }
}
