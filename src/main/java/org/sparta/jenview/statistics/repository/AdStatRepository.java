package org.sparta.jenview.statistics.repository;

import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.sparta.jenview.statistics.entity.AdStatEntity;
import org.sparta.jenview.statistics.entity.AdStatId;
import org.sparta.jenview.statistics.entity.VideoStatEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdStatRepository extends JpaRepository<AdStatEntity, AdStatId> {

    @Query("SELECT a FROM AdStatEntity a WHERE a.videoId.id = :videoId AND a.createdAt BETWEEN :start AND :end ORDER BY a.createdAt ASC")
    List<AdStatEntity> findByVideoIdIdAndCreatedAtBetween(@Param("videoId") Long videoId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Query("SELECT a FROM AdStatEntity a WHERE a.createdAt BETWEEN :start AND :end ORDER BY a.viewCount DESC")
    List<AdStatEntity> findTop5ByViewCount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);


    List<AdStatEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
