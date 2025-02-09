package com.example.game_web.authentication.emailVerification.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class CompleteEvent extends ApplicationEvent {
    private String email;
    private Integer codeVerify;

    public CompleteEvent(String email, Integer code) {
        super(email);
        this.email= email;
        this.codeVerify = code;
    }
}
