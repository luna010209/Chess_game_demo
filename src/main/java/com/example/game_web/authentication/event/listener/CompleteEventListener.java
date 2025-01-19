package com.example.game_web.authentication.event.listener;

import com.example.game_web.authentication.event.dto.CompleteEvent;
import com.example.game_web.authentication.event.vertify.VerifiedCode;
import com.example.game_web.authentication.event.vertify.VerifiedCodeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompleteEventListener implements ApplicationListener<CompleteEvent> {
    private final VerifiedCodeRepo codeRepo;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(CompleteEvent event) {
        // Create a verification code for email of new user
        SecureRandom random = new SecureRandom();
        Integer verifiedCode = 100000 + random.nextInt(900000);
            // In case of generating a string token: c4c31b74-847c-4ea7-a3bb-d2c68e1bdcf2
            // String token = UUID.randomUUID().toString();
        // Save into DB
        codeRepo.save(new VerifiedCode(verifiedCode, event.getEmail()));
        log.info(verifiedCode.toString());
    }
}
