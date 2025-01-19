package com.example.game_web.authentication.component;

import com.example.game_web.authentication.user.entity.UserEntity;
import com.example.game_web.authentication.user.repo.UserRepo;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserComponent {
    private final UserRepo userRepo;
    public UserEntity userLogin(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepo.findByUsername(username).orElseThrow(
                ()-> new CustomException("No exist user login!!!", HttpStatus.NOT_FOUND)
        );
        return userRepo.userWithAuth(userEntity.getId()).orElseThrow();
    }

    public UserEntity userById(Long id){
        UserEntity user = userRepo.userWithAuth(id).orElseThrow(
                ()-> new CustomException("No exist user!!!", HttpStatus.NOT_FOUND)
        );
        return user;
    }
}
