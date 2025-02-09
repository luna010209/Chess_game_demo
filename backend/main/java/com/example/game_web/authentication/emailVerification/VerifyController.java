package com.example.game_web.authentication.emailVerification;

import com.example.game_web.authentication.emailVerification.dto.RequestEmail;
import com.example.game_web.authentication.emailVerification.vertify.VerifiedCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("code")
public class VerifyController {
    private final VerifyService verifyService;
//    @EventListener(ApplicationContextEvent.class)
    @PostMapping("send")
    public String sendCode(@RequestBody RequestEmail requestEmail){
        return verifyService.sendCode(requestEmail);
    }

    @PostMapping("verify")
    public String verifyEmail(@RequestBody RequestEmail requestEmail){
        return verifyService.verifyEmail(requestEmail);
    }
}
