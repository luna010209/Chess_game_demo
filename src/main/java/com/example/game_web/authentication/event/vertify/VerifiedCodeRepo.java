package com.example.game_web.authentication.event.vertify;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifiedCodeRepo extends JpaRepository<VerifiedCode, Long> {

}
