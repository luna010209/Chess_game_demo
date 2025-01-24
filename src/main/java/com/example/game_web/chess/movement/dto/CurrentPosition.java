package com.example.game_web.chess.movement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentPosition {
    private int rowIdx;
    private int colIdx;
    private boolean isWhite;
    private String piece;
}
