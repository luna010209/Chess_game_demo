package com.example.game_web.authentication.event.vertify;

import com.example.game_web.authentication.user.entity.UserEntity;
import com.example.game_web.base.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;

@Getter
@Entity
public class VerifiedCode extends EntityBase {
    private Integer code;
    private Date expirationTime;
    private String email;
    private boolean isSuccess;
//    @OneToOne
//    private UserEntity user;

    public VerifiedCode(Integer code, String email){
        this.code = code;
        this.email = email;
        this.isSuccess = false;
        this.expirationTime= this.getCodeExpiration();
    }

    public Date getCodeExpiration(){
        Instant now = Instant.now();
        return Date.from(now.plusSeconds(60*10));
    }
}
