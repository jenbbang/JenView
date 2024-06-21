package org.sparta.jenview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sparta.jenview.entity.JenViewEntity;

@Repository
public interface JenViewRepository extends JpaRepository<JenViewEntity, Long> {
    // 커스텀 메소드 정의 가능
}
