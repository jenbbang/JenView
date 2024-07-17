package org.sparta.jenview.Calculate.repository;

import org.sparta.jenview.Calculate.entity.VideoCalcEntity;
import org.sparta.jenview.Calculate.entity.VideoCalcId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoCalcRepository extends JpaRepository<VideoCalcEntity, VideoCalcId> {
    List<VideoCalcEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    boolean existsByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}


