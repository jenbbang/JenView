package org.sparta.jenview.Settlement.repository;

import org.sparta.jenview.Settlement.entity.VideoCalcEntity;
import org.sparta.jenview.Settlement.entity.VideoCalcId;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoCalcRepository extends JpaRepository<VideoCalcEntity, VideoCalcId> {
    List<VideoCalcEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}


