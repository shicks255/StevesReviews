package com.steven.hicks.repositories;

import com.steven.hicks.models.metrics.ArtistMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistMetricsRepository extends JpaRepository<ArtistMetrics, String> {

}
