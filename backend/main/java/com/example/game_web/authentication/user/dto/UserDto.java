package com.example.game_web.authentication.user.dto;

import com.example.game_web.authentication.user.entity.Authority;
import com.example.game_web.authentication.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String name;
    private String email;
    private final Set<String> authorities = new HashSet<>();
    public static UserDto fromEntity(UserEntity entity) {
        UserDto dto = UserDto.builder()
                .username(entity.getUsername())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
        for (Authority authority: entity.getAuthorities())
            dto.authorities.add(authority.getRole());
        return dto;
    }
}
