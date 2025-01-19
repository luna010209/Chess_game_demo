package com.example.game_web.authentication.user.service;

import com.example.game_web.authentication.component.UserComponent;
import com.example.game_web.authentication.user.dto.UserDto;
import com.example.game_web.authentication.user.dto.UserRequest;
import com.example.game_web.authentication.user.dto.UserResponse;
import com.example.game_web.authentication.user.entity.Authority;
import com.example.game_web.authentication.user.entity.UserEntity;
import com.example.game_web.authentication.user.repo.AuthorityRepo;
import com.example.game_web.authentication.user.repo.UserRepo;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final AuthorityRepo authorityRepo;
    private final UserComponent userComponent;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username).orElseThrow(
                ()-> new CustomException("No exist username!!!", HttpStatus.UNAUTHORIZED)
        );
        return (UserDetails) UserResponse.fromEntity(userRepo.userWithAuth(user.getId()).orElseThrow());
    }

    public UserDto newUser(UserRequest request){
        if (userRepo.existsByUsername(request.getUsername()))
            throw new CustomException("Username already exists!!!", HttpStatus.CONFLICT);
        else if (userRepo.existsByEmail(request.getEmail()))
            throw new CustomException("Email already exists!!!", HttpStatus.CONFLICT);
        else if (!request.getPassword().equals(request.getConfirmPw()))
            throw new CustomException("Password and confirm password do not match!!!", HttpStatus.CONFLICT);
        Authority userRole = authorityRepo.findByRole("ROLE_USER").orElseThrow();
        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .name(request.getName())
                .avatar("")
                .build();
        userEntity.getAuthorities().add(userRole);
        return UserDto.fromEntity(userRepo.save(userEntity));
    }

    public UserDto userLogin(){
        return UserDto.fromEntity(userComponent.userLogin());
    }
}
