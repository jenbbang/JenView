package org.sparta.jenview.plays.repository;

import org.sparta.jenview.plays.entity.AdPlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdPlayRepository extends JpaRepository<AdPlayEntity, Long> {

}
