package com.example.game_web.chess.movement.piece.bishop;

import com.example.game_web.chess.board.entity.ChessGame;
import com.example.game_web.chess.board.repo.ChessGameRepo;
import com.example.game_web.chess.board.repo.ChessPieceRepo;
import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BishopMove {
    private final ChessGameRepo gameRepo;
    private final ChessPieceRepo pieceRepo;
    public List<NewPosition> listMove(CurrentPosition bishop){
        List<NewPosition> list = new ArrayList<>();
        ChessGame game = gameRepo.findById(bishop.getGameId()).orElseThrow(
                ()-> new CustomException("No exist game", HttpStatus.BAD_REQUEST)
        );
        int colCur = bishop.getColIdx();
        int rowCur = bishop.getRowIdx();
        int colLoop = colCur;
        for (int row = rowCur -1; row>=0; row--){
            if (colLoop++<7 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                    row, colLoop+++1, bishop.isWhite(), game
            )) {
                list.add(NewPosition.builder().rowIdx(row).colIdx(colLoop+++1).build());
                // If the position has opponent's piece, break the loop
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        row, colLoop+++1, !bishop.isWhite(), game
                )) break;
            }
            else break;
        }
        colLoop = colCur;
        for (int row = rowCur -1; row>=0; row--){
            if (colLoop-->0 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                    row, colLoop---1, bishop.isWhite(), game
            )) {
                list.add(NewPosition.builder().rowIdx(row).colIdx(colLoop---1).build());
                // If the position has opponent's piece, break the loop
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        row, colLoop---1, !bishop.isWhite(), game
                )) break;
            }
            else break;
        }
        colLoop = colCur;
        for (int row = rowCur +1; row<8; row++){
            if (colLoop++<7 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                    row, colLoop+++1, bishop.isWhite(), game
            )) {
                list.add(NewPosition.builder().rowIdx(row).colIdx(colLoop+++1).build());
                // If the position has opponent's piece, break the loop
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        row, colLoop+++1, !bishop.isWhite(), game
                )) break;
            }
            else break;
        }
        colLoop = colCur;
        for (int row = rowCur +1; row<8; row++){
            if (colLoop-->0 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                    row, colLoop---1, bishop.isWhite(), game
            )) {
                list.add(NewPosition.builder().rowIdx(row).colIdx(colLoop---1).build());
                // If the position has opponent's piece, break the loop
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        row, colLoop---1, !bishop.isWhite(), game
                )) break;
            }
            else break;
        }
        return list;
    }
}
