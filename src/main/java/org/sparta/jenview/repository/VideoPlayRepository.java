package org.sparta.jenview.repository;

import org.sparta.jenview.entity.VideoPlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoPlayRepository extends JpaRepository<VideoPlayEntity, Long> {
    List<VideoPlayEntity> findByVideoEntity_IdAndUserEntity_Id(Long videoId, Long userId);
}
