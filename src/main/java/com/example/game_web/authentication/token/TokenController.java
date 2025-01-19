package com.example.game_web.authentication.token;

import com.example.game_web.authentication.token.dto.TokenRequest;
import com.example.game_web.authentication.token.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;
    @PostMapping("issue")
    public TokenResponse getToken(@RequestBody TokenRequest request){
        return tokenService.getToken(request);
    }
    @PostMapping("check/{token}")
    public boolean validate(@PathVariable("token") String token){
        return tokenService.validate(token);
    }
}
