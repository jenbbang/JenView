package org.sparta.jenview.plays.repository;

import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdPlayRepository extends JpaRepository<AdPlayEntity, Long> {
    Optional<AdPlayEntity> findTopByVideoId_IdOrderByPlayTimeDesc(Long videoId);
    List<AdPlayEntity> findByVideoId_Id(Long videoId);
}
