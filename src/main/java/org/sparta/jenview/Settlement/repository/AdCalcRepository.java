package org.sparta.jenview.Settlement.repository;

import org.sparta.jenview.Settlement.entity.AdCalcEntity;
import org.sparta.jenview.Settlement.entity.AdCalcId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdCalcRepository extends JpaRepository<AdCalcEntity, AdCalcId> {
}
