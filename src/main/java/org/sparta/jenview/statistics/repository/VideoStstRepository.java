package org.sparta.jenview.statistics.repository;

import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.sparta.jenview.statistics.entity.VideoStatId;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoStstRepository  extends JpaRepository<VideoStatEntity, VideoStatId> {
    List<VideoStatEntity> findTop5ByCreatedAtBetweenOrderByViewCountDesc(LocalDateTime startDate, LocalDateTime endDate);
    List<VideoStatEntity> findTop5ByCreatedAtBetweenOrderByTotalPlayTimeDesc(LocalDateTime startDate, LocalDateTime endDate);
    VideoStatEntity findTopByVideoIdOrderByCreatedAtDesc(VideoEntity videoEntity);
}
