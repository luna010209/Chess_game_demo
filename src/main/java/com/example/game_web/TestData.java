package com.example.game_web;

import com.example.game_web.authentication.user.entity.Authority;
import com.example.game_web.authentication.user.entity.UserEntity;
import com.example.game_web.authentication.user.repo.AuthorityRepo;
import com.example.game_web.authentication.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class TestData {
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder encoder;

    public TestData(UserRepo userRepo, AuthorityRepo authorityRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
        this.encoder = encoder;
        allAccounts();
    }

    public void allAccounts(){
        Authority roleAdmin = authorityRepo.findByRole("ROLE_ADMIN").orElseThrow();
        Authority roleUser = authorityRepo.findByRole("ROLE_USER").orElseThrow();
        UserEntity user1 = UserEntity.builder()
                .username("luna010209")
                .password(encoder.encode("Luna@01"))
                .name("Luna Do")
                .email("luna@gmail.com")
                .avatar("")
                .build();
        user1.getAuthorities().add(roleAdmin);
        user1.getAuthorities().add(roleUser);
        userRepo.save(user1);
    }
}
