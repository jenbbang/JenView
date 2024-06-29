package org.sparta.jenview.repository;

import org.sparta.jenview.entity.VideoPlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoPlayRepository extends JpaRepository<VideoPlayEntity, Long> {
}
