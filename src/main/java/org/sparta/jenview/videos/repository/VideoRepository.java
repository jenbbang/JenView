package org.sparta.jenview.videos.repository;

import jakarta.transaction.Transactional;
import org.sparta.jenview.videos.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

//    Optional<VideoEntity> findByVideoId(Long Id);

    @Modifying
    @Transactional
    @Query("DELETE FROM VideoEntity v WHERE v.userEntity.id = :userId")
    void deleteByUserId(@Param("userId")Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VideoEntity v WHERE v.id = :id")
    void deleteByVideoId(@Param("id") Long id);

    @Query("SELECT v.id FROM VideoEntity v")
    List<Long> findAllVideoIds();
}