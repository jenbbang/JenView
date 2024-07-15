package org.sparta.jenview.plays.repository;

import org.sparta.jenview.plays.entity.VideoPlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoPlayRepository extends JpaRepository<VideoPlayEntity, Long> {
    List<VideoPlayEntity> findByVideoEntity_IdAndUserEntity_Id(Long videoId, Long userId);
    List<VideoPlayEntity> findByVideoEntity_Id(Long videoId); // 추가된 메서드
    List<VideoPlayEntity> findByVideoEntity_IdAndCreatedAtBetween(Long videoId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT DISTINCT v.videoEntity.id FROM VideoPlayEntity v WHERE v.createdAt BETWEEN :start AND :end")
    List<Long> findDistinctVideoEntity_IdByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
