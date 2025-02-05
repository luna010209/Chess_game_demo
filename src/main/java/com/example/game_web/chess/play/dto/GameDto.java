package com.example.game_web.chess.play.dto;

import com.example.game_web.chess.board.entity.ChessGame;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GameDto {
    private Long id;
    private boolean whiteTurn;
    private boolean kingDanger;
    private String status;
    public static GameDto dto(ChessGame game){
        GameDto gameDto = GameDto.builder()
                .id(game.getId())
                .whiteTurn(game.isWhiteTurn())
                .kingDanger(game.isKingDanger())
                .status(game.getStatus().toString())
                .build();
        return gameDto;
    }
}
