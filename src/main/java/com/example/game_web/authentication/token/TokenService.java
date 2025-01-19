package com.example.game_web.authentication.token;

import com.example.game_web.authentication.token.dto.TokenRequest;
import com.example.game_web.authentication.token.dto.TokenResponse;
import com.example.game_web.authentication.user.service.UserService;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final PasswordEncoder encoder;
    public TokenResponse getToken(TokenRequest request){
        UserDetails user = userService.loadUserByUsername(request.getUsername());
        if (!encoder.matches(request.getPassword(), user.getPassword()))
            throw new CustomException("Wrong password!!!", HttpStatus.CONFLICT);
        TokenResponse response = TokenResponse.builder()
                .token(tokenUtils.generateToken(request.getUsername())).build();
        return response;
    }

    public boolean validate(String token){
        return tokenUtils.validate(token);
    }
}
