package com.steven.hicks.services;

import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class ArtistService {

    @Autowired
    ArtistRepository m_artistRepository;

    public Artist getArtistById(Supplier<Integer> id) {
        return m_artistRepository.findById(id.get())
                .orElseThrow();
    }
}
