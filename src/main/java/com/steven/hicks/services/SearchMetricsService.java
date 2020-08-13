package com.steven.hicks.services;

import com.steven.hicks.models.metrics.AlbumMetrics;
import com.steven.hicks.models.metrics.SearchMetrics;
import com.steven.hicks.repositories.SearchMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchMetricsService {
    @Autowired
    private SearchMetricsRepository m_searchMetricsRepository;

    public void upsert(String search) {
        if (m_searchMetricsRepository.existsById(search)) {
            SearchMetrics metrics = m_searchMetricsRepository.getOne(search);
            metrics.setHits(metrics.getHits() + 1);
            m_searchMetricsRepository.save(metrics);
        } else {
            SearchMetrics metrics = new SearchMetrics();
            metrics.setName(search);
            metrics.setHits(1);
            m_searchMetricsRepository.save(metrics);
        }
    }
}
