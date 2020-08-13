package com.steven.hicks.models.metrics;

import com.steven.hicks.models.AuditClass;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "search_metrics")
public class SearchMetrics extends AuditClass {
    @Id
    private String name = "";
    private long hits;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }
}
