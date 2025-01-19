package com.example.game_web.authentication.user.service;

import com.example.game_web.authentication.user.entity.Authority;
import com.example.game_web.authentication.user.repo.AuthorityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    private final AuthorityRepo authorityRepo;
    public static String[] roles = {"ROLE_USER", "ROLE_ADMIN"};
    public AuthorityService(AuthorityRepo authorityRepo){
        this.authorityRepo = authorityRepo;
        for (String role: roles){
            if (!authorityRepo.existsByRole(role))
                authorityRepo.save(Authority.builder().role(role).build());
        }
    }
}
