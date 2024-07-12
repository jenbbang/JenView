package org.sparta.jenview.plays.repository;

import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdPlayRepository extends JpaRepository<AdPlayEntity, Long> {
    Optional<AdPlayEntity> findTopByVideoId_IdOrderByPlayTimeDesc(Long videoId);
//    // Long 타입을 사용하여 쿼리 메서드 수정
    List<AdPlayEntity>  findAll();
////    void deleteById(Long id);
}
