package com.example.game_web.chess.board.entity;

import com.example.game_web.base.EntityBase;
import jakarta.persistence.Entity;

@Entity
public class ChessPiece extends EntityBase {
    private String piece;
    private int colIdx;
    private int rowIdx;
    private boolean isWhite;
}
