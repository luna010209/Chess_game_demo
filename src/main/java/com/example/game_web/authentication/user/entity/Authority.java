package com.example.game_web.authentication.user.entity;

import com.example.game_web.base.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Authority extends EntityBase {
    private String role;
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private final Set<UserEntity> users = new HashSet<>();
}
