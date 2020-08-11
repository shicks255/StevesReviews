package com.steven.hicks.repositories;

import com.steven.hicks.models.metrics.AlbumMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumMetricsRepository extends JpaRepository<AlbumMetrics, String> {

}
