package com.example.game_web.authentication.emailVerification.vertify;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifiedCodeRepo extends JpaRepository<VerifiedCode, Long> {
    Optional<VerifiedCode> findByEmail(String email);
    boolean existsByEmailAndIsSuccess(String email, boolean isSuccess);
}
