package com.steven.hicks.services;

import com.steven.hicks.models.metrics.ArtistMetrics;
import com.steven.hicks.repositories.ArtistMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistMetricsService {

    @Autowired
    private ArtistMetricsRepository m_artistMetricsRepository;

    public void upsertArtistMetrics(String id, String name) {
        if (m_artistMetricsRepository.existsById(id)) {
            ArtistMetrics visitor = m_artistMetricsRepository.getOne(id);
            visitor.setHits(visitor.getHits() + 1);
            m_artistMetricsRepository.save(visitor);
        } else {
            ArtistMetrics metrics = new ArtistMetrics();
            metrics.setId(id);
            metrics.setHits(1);
            metrics.setName(name);
            m_artistMetricsRepository.save(metrics);
        }
    }

}
