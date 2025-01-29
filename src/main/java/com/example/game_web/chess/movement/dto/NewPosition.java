package com.example.game_web.chess.movement.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EqualsAndHashCode
public class NewPosition {
    private int rowIdx;
    private int colIdx;
//    private boolean isWhite;
//    private String piece;

}
