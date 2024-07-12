package org.sparta.jenview.ad.repository;

import org.sparta.jenview.ad.entity.AdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Long> {
    @Modifying
    @Query("DELETE FROM AdEntity a WHERE a.video.id = :videoId")
    void deleteByVideoId(@Param("videoId") Long videoId);

    @Query("SELECT a FROM AdEntity a WHERE a.video.id = :videoId")
    List<AdEntity> findByVideoId(@Param("videoId") Long videoId);

    //모든 video id 가져오기
    @Query("SELECT v.id FROM VideoEntity v")
    List<Long> findAllVideoIds();


}
