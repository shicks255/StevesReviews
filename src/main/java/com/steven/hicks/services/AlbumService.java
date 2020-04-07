package com.steven.hicks.services;

import com.steven.hicks.models.album.Album;
import com.steven.hicks.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository m_albumRepository;

    public Album getById(int id) {
        return m_albumRepository.getOne(id);
    }
}
