package org.sparta.jenview.repository;

import org.sparta.jenview.entity.AdPlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdPlayRepository extends JpaRepository<AdPlayEntity, Long> {

}
