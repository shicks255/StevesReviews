package com.steven.hicks.repositories;

import com.steven.hicks.models.metrics.SearchMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchMetricsRepository extends JpaRepository<SearchMetrics, String> {

}
