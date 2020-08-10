package com.steven.hicks.repositories;

import com.steven.hicks.models.metrics.VisitorMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<VisitorMetrics, String> {

}
