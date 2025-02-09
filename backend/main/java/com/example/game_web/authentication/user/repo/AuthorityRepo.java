package com.example.game_web.authentication.user.repo;

import com.example.game_web.authentication.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepo extends JpaRepository<Authority, Long> {
    boolean existsByRole(String role);
    Optional<Authority> findByRole(String role);
}
