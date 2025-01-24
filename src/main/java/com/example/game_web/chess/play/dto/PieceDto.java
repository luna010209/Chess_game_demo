package com.example.game_web.chess.play.dto;

import lombok.Getter;

@Getter
public class PieceDto {
    private boolean isWhiteTurn;
    private boolean isWhite;
    private String piece;
    private int rowIdx;
    private int colIdx;
}
