package com.example.game_web.authentication.emailVerification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestEmail {
    private String email;
    private Integer code;
}
