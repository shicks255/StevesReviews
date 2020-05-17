package com.steven.hicks.scheduler;

import com.steven.hicks.models.ArtistSync;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.repositories.ArtistSyncRepository;
import com.steven.hicks.services.AlbumService;
import com.steven.hicks.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Job {

    @Autowired
    ArtistService m_artistService;
    @Autowired
    AlbumService m_albumService;
    @Autowired
    ArtistSyncRepository m_artistSyncRepository;

    @Scheduled(initialDelay = 5_000, fixedDelay = 1000*60*60)
    public void doStuff() {
        System.out.println("Doing stuff");

        //First make sure all artists are in the sync table
        List<Artist> artists = m_artistService.getAllArtists();
        for (Artist x : artists)
        {
            ArtistSync sync = m_artistSyncRepository.findById(x.getId()).orElseGet(() -> {
                ArtistSync newSync = new ArtistSync();
                newSync.setArtistId(x.getId());
                return m_artistSyncRepository.save(newSync);
            });
        }

        //Get the oldest artist_sync record, or a null
        String artistToSync = m_artistSyncRepository.getNextArtistToSync();
        System.out.println("Snyinc " + artistToSync);
    }

}
