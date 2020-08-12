package com.steven.hicks.services;

import com.steven.hicks.models.metrics.AlbumMetrics;
import com.steven.hicks.repositories.AlbumMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumMetricsService {

    @Autowired
    private AlbumMetricsRepository m_albumMetricsRepository;

    public void upsertAlbumMetrics(String id, String albumName) {
        if (m_albumMetricsRepository.existsById(id)) {
            AlbumMetrics visitor = m_albumMetricsRepository.getOne(id);
            visitor.setHits(visitor.getHits() + 1);
            m_albumMetricsRepository.save(visitor);
        } else {
            AlbumMetrics metrics = new AlbumMetrics();
            metrics.setId(id);
            metrics.setHits(1);
            metrics.setAlbumName(albumName);
            m_albumMetricsRepository.save(metrics);
        }
    }
}
