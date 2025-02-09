package com.example.game_web.authentication.user.repo;

import com.example.game_web.authentication.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    @Query("SELECT DISTINCT u FROM UserEntity u JOIN FETCH u.authorities WHERE u.id=:id")
    Optional<UserEntity> userWithAuth(Long id);

    Optional<UserEntity> findByUsername (String username);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
