package com.example.game_web.chess.play.dto;

import com.example.game_web.chess.board.entity.ChessPiece;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class ResponseDto {
    private Long pieceId;
    private String piece;
    private int rowIdx;
    private int colIdx;
    private boolean white;
    private Long gameId;
    private boolean whiteTurn;
    private boolean kingDanger;
    private boolean finish;
    public static ResponseDto fromEntity(ChessPiece piece){
        ResponseDto dto = ResponseDto.builder()
                .pieceId(piece.getId())
                .piece(piece.getPiece())
                .rowIdx(piece.getRowIdx())
                .colIdx(piece.getColIdx())
                .white(piece.isWhite())
                .gameId(piece.getChessGame().getId())
                .whiteTurn(piece.getChessGame().isWhiteTurn())
                .kingDanger(piece.getChessGame().isKingDanger())
                .finish(piece.getChessGame().isFinish())
                .build();
        return dto;
    }
}
