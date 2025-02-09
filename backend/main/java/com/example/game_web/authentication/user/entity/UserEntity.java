package com.example.game_web.authentication.user.entity;

import com.example.game_web.base.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity extends EntityBase {
    private String username;
    private String password;
    private String name;
    private String email;
    private String avatar;
    @ManyToMany(fetch = FetchType.LAZY)
    private final Set<Authority> authorities = new HashSet<>();
}
