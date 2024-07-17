package org.sparta.jenview.Calculate.repository;

import org.sparta.jenview.Calculate.entity.AdCalcEntity;
import org.sparta.jenview.Calculate.entity.AdCalcId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AdCalcRepository extends JpaRepository<AdCalcEntity, AdCalcId> {
    boolean existsByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
