package com.steven.hicks.scheduler;

import com.fasterxml.jackson.databind.JsonNode;
import com.steven.hicks.logic.musicBrainz.MBAlbumSearcher;
import com.steven.hicks.logic.musicBrainz.MBArtistSearcher;
import com.steven.hicks.models.ArtistSync;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.album.AlbumImage;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.models.artist.ArtistImage;
import com.steven.hicks.repositories.*;
import com.steven.hicks.services.AlbumService;
import com.steven.hicks.services.ArtistService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class Job {

    @Autowired
    ArtistService m_artistService;
    @Autowired
    ArtistImageRepository m_artistImageRepository;
    @Autowired
    AlbumService m_albumService;
    @Autowired
    AlbumRepository m_albumRepository;
    @Autowired
    AlbumImageRepository m_albumImageRepository;
    @Autowired
    ArtistSyncRepository m_artistSyncRepository;
    @Autowired
    MeterRegistry m_meterRegistry;

    MBArtistSearcher m_mbArtistSearcher = new MBArtistSearcher();
    MBAlbumSearcher m_mbAlbumSearcher = new MBAlbumSearcher();

    @Value("${fanArtKey}")
    private String FAN_ART_KEY = "";

    @Scheduled(initialDelay = 5_000, fixedDelay = 1000*60*15)
    public void doStuff() {
        m_meterRegistry.counter("artistSync").increment();

        //First make sure all artists are in the sync table
        List<Artist> artists = m_artistService.getAllArtists();
        for (Artist x : artists)
        {
            if (!m_artistSyncRepository.existsById(x.getId())) {
                ArtistSync newSync = new ArtistSync();
                newSync.setArtistId(x.getId());
                m_artistSyncRepository.save(newSync);
            }
        }

        //Get the oldest artist_sync record, or a null
        String artistToSync = m_artistSyncRepository.getNextArtistToSync();
        System.out.println("Syncing artist ID " + artistToSync);
        try
        {
            Artist artistModel = m_artistService.getArtist(artistToSync);
            JsonNode artist = m_mbArtistSearcher.getArtistWithImages(artistToSync, FAN_ART_KEY);

            //Update ArtistImage
            JsonNode artistImages = artist.get("images");
            for (Iterator<JsonNode> iterator = artistImages.iterator(); iterator.hasNext(); )
            {
                ArtistImage image = new ArtistImage();
                JsonNode img = iterator.next();
                String t = img.findValue("#text").asText();

                image.setUrl(t);
                image.setArtist(artistModel);
                if (!m_artistImageRepository.existsAllByUrl(image.getUrl()))
                    m_artistImageRepository.save(image);
            }

            JsonNode releaseDates = m_mbAlbumSearcher.searchForAlbumsByArtist(artistToSync, 0);
            Map<String, String> releaseToDate = new HashMap<>();
            for (Iterator<JsonNode> iterator = releaseDates.iterator(); iterator.hasNext(); )
            {
                JsonNode thing = iterator.next();
                String idd = thing.get("id").asText();
                String date = thing.get("first-release-date").asText();
                releaseToDate.put(idd, date);
            }

            //get any new albums
            JsonNode albums = m_mbAlbumSearcher.searchForAlbumsByArtist(artistToSync, 0);
            List<Album> albumSet = new ArrayList<>();
            List<AlbumImage> albumImages = new ArrayList<>();
            for (Iterator<JsonNode> it = albums.iterator(); it.hasNext(); )
            {
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
                album.setArtist(artistModel);

                JsonNode albumImagesa = m_mbAlbumSearcher.getReleaseGroupArt(albumId);
                if (albumImagesa != null)
                {
                    for (Iterator<JsonNode> itt = albumImagesa.iterator(); itt.hasNext(); )
                    {
                        JsonNode x = itt.next();
                        getAlbumImages(albumImages, album, x);
                    }
                    albumSet.add(album);
                }
            }

            albumSet.removeIf(x -> !x.getType().equalsIgnoreCase("Album") && !x.getType().equalsIgnoreCase("Ep"));
            albumSet.forEach(x -> {
                if (!m_albumRepository.existsById(x.getId()))
                    m_albumRepository.save(x);
            });

            albumImages.forEach(x -> {
                if (!m_albumImageRepository.existsAllByUrl(x.getUrl()))
                    m_albumImageRepository.save(x);
            });
        } catch (Exception e)
        {
            System.out.println("Problem syncing artist " + artistToSync);
            System.out.println(e.getMessage());
        }
        finally
        {
            m_artistSyncRepository.updateLastSync(LocalDateTime.now(), artistToSync);
        }

        System.out.println("Finished syncing " + artistToSync);
    }

    private void getAlbumImages(List<AlbumImage> albumImages, Album album, JsonNode x) {
        String text = "";
        if (x.has("front") && x.get("front").asText().equals("true"))
            text = "front";

        if (x.has("back") && x.get("back").asText().equals("true"))
            text = "back";

        if (x.has("types")) {
            JsonNode types = x.get("types");
            for (Iterator<JsonNode> it = types.iterator(); it.hasNext(); ) {
                String type = it.next().asText();
                if (type.equalsIgnoreCase("booklet"))
                    text = "booklet";
                if (type.equalsIgnoreCase("sping"))
                    text = "spine";
                if (type.equalsIgnoreCase("tray"))
                    text = "tray";
                if (type.equalsIgnoreCase("medium"))
                    text = "medium";
                if (type.equalsIgnoreCase("other"))
                    text = "other";
            }
        }

        if (text.length() > 0)
        {
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
                if (thumbnails.has("250"))
                {
                    AlbumImage thum_250 = new AlbumImage();
                    thum_250.setSize("250");
                    thum_250.setAlbum(album);
                    thum_250.setText(text);
                    thum_250.setUrl(thumbnails.get("250").asText());
                    albumImages.add(thum_250);
                }
                if (thumbnails.has("500"))
                {
                    AlbumImage thumb_500 = new AlbumImage();
                    thumb_500.setSize("500");
                    thumb_500.setAlbum(album);
                    thumb_500.setText(text);
                    thumb_500.setUrl(thumbnails.get("500").asText());
                    albumImages.add(thumb_500);
                }
                if (thumbnails.has("1200"))
                {
                    AlbumImage thum_1200 = new AlbumImage();
                    thum_1200.setSize("1200");
                    thum_1200.setAlbum(album);
                    thum_1200.setText(text);
                    thum_1200.setUrl(thumbnails.get("1200").asText());
                    albumImages.add(thum_1200);
                }
                if (thumbnails.has("large"))
                {
                    AlbumImage thum_large = new AlbumImage();
                    thum_large.setSize("large");
                    thum_large.setAlbum(album);
                    thum_large.setText(text);
                    thum_large.setUrl(thumbnails.get("large").asText());
                    albumImages.add(thum_large);
                }
                if (thumbnails.has(("small")))
                {
                    AlbumImage thumb_small = new AlbumImage();
                    thumb_small.setSize("small");
                    thumb_small.setAlbum(album);
                    thumb_small.setText(text);
                    thumb_small.setUrl(thumbnails.get("small").asText());
                    albumImages.add(thumb_small);
                }
            }
        }
    }

}
