package com.example.game_web.authentication.event;

import com.example.game_web.authentication.event.dto.RequestEmail;
import com.example.game_web.authentication.event.vertify.VerifiedCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("verify")
public class VerifyController {
    private final VerifyService verifyService;
    @EventListener(ApplicationContextEvent.class)
    public VerifiedCode sendCode(@RequestBody RequestEmail requestEmail){
        return verifyService.verifyEmail(requestEmail);
    }
}
