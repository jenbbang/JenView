package org.sparta.jenview.statistics.repository;

import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.sparta.jenview.statistics.entity.VideoStatId;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoStatRepository extends JpaRepository<VideoStatEntity, VideoStatId> {
    VideoStatEntity findTopByVideoIdOrderByCreatedAtDesc(VideoEntity videoEntity);

    @Query("SELECT v FROM VideoStatEntity v WHERE v.createdAt BETWEEN :start AND :end ORDER BY v.viewCount DESC")
    List<VideoStatEntity> findTop5ByViewCount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("SELECT v FROM VideoStatEntity v WHERE v.createdAt BETWEEN :start AND :end ORDER BY v.totalPlayTime DESC")
    List<VideoStatEntity> findTop5ByTotalPlayTime(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    List<VideoStatEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
