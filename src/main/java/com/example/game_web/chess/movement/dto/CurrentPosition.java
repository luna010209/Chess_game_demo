package com.example.game_web.chess.movement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentPosition {
    private int rowIdx;
    private int colIdx;
    private boolean isWhite;
    private String piece;
}
