package com.example.game_web.chess.movement.piece.bishop;

import com.example.game_web.chess.board.entity.ChessGame;
import com.example.game_web.chess.board.repo.ChessGameRepo;
import com.example.game_web.chess.board.repo.ChessPieceRepo;
import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
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
        log.info(String.valueOf(rowCur) + " "+ String.valueOf(colCur));
        int colLoop = colCur;
        for (int row = rowCur -1; row>=0; row--){
            int col = colLoop+++1;
            if (col<8 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                    row, col, bishop.isWhite(), game
            )) {
                list.add(NewPosition.builder().rowIdx(row).colIdx(col).build());
                // If the position has opponent's piece, break the loop
                if (pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                        row, col, !bishop.isWhite(), game
                )) break;
            }
            else break;
        }
        colLoop = colCur;
        for (int row = rowCur -1; row>=0; row--){
            int col = colLoop---1;
            if (col>=0 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                    row, col, bishop.isWhite(), game
            )) {
                list.add(NewPosition.builder().rowIdx(row).colIdx(col).build());
                // If the position has opponent's piece, break the loop
                if (pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                        row, col, !bishop.isWhite(), game
                )) break;
            }
            else break;
        }
        colLoop = colCur;
        for (int row = rowCur +1; row<8; row++){
            int col = colLoop+++1;
            log.info(String.valueOf(row));
            log.info(String.valueOf(col));
            if (col<8 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                    row, col, bishop.isWhite(), game
            )) {
                list.add(NewPosition.builder().rowIdx(row).colIdx(col).build());
                // If the position has opponent's piece, break the loop
                if (pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                        row, col, !bishop.isWhite(), game
                )) break;
            }
            else break;
        }
        colLoop = colCur;
        for (int row = rowCur +1; row<8; row++){
            int col = colLoop---1;
            if (col>=0 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                    row, col, bishop.isWhite(), game
            )) {
                list.add(NewPosition.builder().rowIdx(row).colIdx(col).build());
                // If the position has opponent's piece, break the loop
                if (pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                        row, col, !bishop.isWhite(), game
                )) break;
            }
            else break;
        }
        return list;
    }
}
