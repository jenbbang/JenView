package org.sparta.jenview.repository;

import org.sparta.jenview.entity.AdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Long> {
    List<AdEntity> findByVideoId(Long videoId);

}
