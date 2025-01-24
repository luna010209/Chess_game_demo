package com.example.game_web.chess.movement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NewPosition {
    private int rowIdx;
    private int colIdx;
//    private boolean isWhite;
//    private String piece;
}
