package com.example.game_web.authentication.user.dto;

import com.example.game_web.authentication.user.entity.Authority;
import com.example.game_web.authentication.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements UserDetails {
    private String username;
    private String password;
    private String name;
    private String email;
    private final Set<String> authoritiesString = new HashSet<>();
    public static UserResponse fromEntity(UserEntity entity){
        UserResponse userResponse = UserResponse.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
        for (Authority authority: entity.getAuthorities())
            userResponse.authoritiesString.add(authority.getRole());
        return userResponse;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authoritiesString.stream().map(SimpleGrantedAuthority::new).toList();
    }
}
