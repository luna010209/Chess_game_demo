package com.example.game_web.chess.board.entity;


import com.example.game_web.base.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChessGame extends EntityBase {
    @Builder.Default
    private boolean whiteTurn= true;
    @Builder.Default
    private boolean kingDanger = false;
//    private boolean finish = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.ONGOING;
    @OneToMany(mappedBy = "chessGame")
    private final List<ChessPiece> pieces = new ArrayList<>();

    public enum Status {
        ONGOING, WIN, DRAW
    }
}
