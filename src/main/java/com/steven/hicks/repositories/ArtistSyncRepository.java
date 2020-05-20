package com.steven.hicks.repositories;

import com.steven.hicks.models.ArtistSync;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface ArtistSyncRepository extends JpaRepository<ArtistSync, String> {

    @Query(value = "select artist_id from artist_sync order by last_sync asc nulls first limit 1", nativeQuery = true)
    String getNextArtistToSync();
    @Modifying
    @Transactional
    @Query(value = "update artist_sync set last_sync = ?1 where artist_id = ?2", nativeQuery = true)
    void updateLastSync(LocalDateTime ldt, String artistId);
}
