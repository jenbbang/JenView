package org.sparta.jenview.repository;

import jakarta.transaction.Transactional;
import org.sparta.jenview.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM VideoEntity v WHERE v.userEntity.id = :userId")
    void deleteByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VideoEntity v WHERE v.id = :videoId")
    void deleteByVideoId(Long videoId);


}







