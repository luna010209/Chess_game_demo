package com.example.game_web.chess.play.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private Long pieceId;
    private Long gameId;
    private String piece;
    private int rowCur;
    private int colCur;
    private int rowNew;
    private int colNew;
    private boolean white;
}
