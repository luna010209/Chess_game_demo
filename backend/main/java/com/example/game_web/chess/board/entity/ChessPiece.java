package com.example.game_web.chess.board.entity;

import com.example.game_web.base.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChessPiece extends EntityBase {
    private String piece;
    private int colIdx;
    private int rowIdx;
    private boolean white;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChessGame chessGame;
}
