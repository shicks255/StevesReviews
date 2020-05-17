package com.steven.hicks.repositories;

import com.steven.hicks.models.ArtistSync;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistSyncRepository extends JpaRepository<ArtistSync, String> {

    @Query(value = "select artist_id from artist_sync order by last_sync asc nulls first limit 1", nativeQuery = true)
    String getNextArtistToSync();
}
