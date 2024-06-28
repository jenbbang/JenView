package org.sparta.jenview.repository;

import jakarta.transaction.Transactional;
import org.sparta.jenview.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.id = :userId")
    void deleteByUserId(Long userId);
}