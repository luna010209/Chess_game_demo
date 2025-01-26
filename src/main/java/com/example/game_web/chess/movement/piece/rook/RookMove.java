package com.example.game_web.chess.movement.piece.rook;

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
public class RookMove {
    private final ChessGameRepo gameRepo;
    private final ChessPieceRepo pieceRepo;

    public List<NewPosition> listMove(CurrentPosition rook){
        List<NewPosition> list = new ArrayList<>();
        ChessGame game = gameRepo.findById(rook.getGameId()).orElseThrow(
                ()-> new CustomException("No exist game", HttpStatus.BAD_REQUEST)
        );
        int colCur = rook.getColIdx();
        int rowCur = rook.getRowIdx();
        for (int col= colCur-1; col>=0; col--){
            if (!pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                    rowCur, col, rook.isWhite(), game
            )){
                list.add(NewPosition.builder().rowIdx(rowCur).colIdx(col).build());
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        rowCur, col, !rook.isWhite(), game
                )) break;
            } else break;
        }
        for (int col= colCur+1; col<8; col++){
            if (!pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                    rowCur, col, rook.isWhite(), game
            )){
                list.add(NewPosition.builder().rowIdx(rowCur).colIdx(col).build());
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        rowCur, col, !rook.isWhite(), game
                )) break;
            } else break;
        }
        for (int row= rowCur-1; row>=0; row--){
            if (!pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                    row, colCur, rook.isWhite(), game
            )){
                list.add(NewPosition.builder().rowIdx(row).colIdx(colCur).build());
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        row, colCur, !rook.isWhite(), game
                )) break;
            } else break;
        }
        for (int row= rowCur+1; row<8; row++){
            if (!pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                    row, colCur, rook.isWhite(), game
            )){
                list.add(NewPosition.builder().rowIdx(row).colIdx(colCur).build());
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        row, colCur, !rook.isWhite(), game
                )) break;
            } else break;
        }
        return list;
    }
}
