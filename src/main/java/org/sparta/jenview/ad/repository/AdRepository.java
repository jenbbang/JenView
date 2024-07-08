package org.sparta.jenview.ad.repository;

import org.sparta.jenview.ad.entity.AdEntity;
import org.sparta.jenview.ad.entity.AdsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, AdsId> {
    List<AdEntity> findByVideoId(Long videoId);
    void deleteByVideoId(Long videoId);

    List<AdEntity> findById(Long id);
}
