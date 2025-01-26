package com.example.game_web.chess.movement.piece.pawn;

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
public class PawnMove {
    private final ChessGameRepo gameRepo;
    private final ChessPieceRepo pieceRepo;
    public List<NewPosition> listMove(CurrentPosition pawn){
        List<NewPosition> list = new ArrayList<>();
        ChessGame game = gameRepo.findById(pawn.getGameId()).orElseThrow(
                ()-> new CustomException("No exist game", HttpStatus.BAD_REQUEST)
        );
        int colCur = pawn.getColIdx();
        int rowCur = pawn.getRowIdx();
        if (pawn.isWhite()){
            if (rowCur<7 && !pieceRepo.existsByRowIdxAndColIdxAndChessGame(
                    rowCur+1, colCur, game
            )){
                list.add(NewPosition.builder().rowIdx(rowCur+1).colIdx(colCur).build());
                if (rowCur==1 && !pieceRepo.existsByRowIdxAndColIdxAndChessGame(
                        rowCur+2, colCur, game
                )) list.add(NewPosition.builder().rowIdx(rowCur+2).colIdx(colCur).build());
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        rowCur+1, colCur+1, !pawn.isWhite(), game
                )) list.add(NewPosition.builder().rowIdx(rowCur+1).colIdx(colCur+1).build());
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        rowCur+1, colCur-1, !pawn.isWhite(), game
                )) list.add(NewPosition.builder().rowIdx(rowCur+1).colIdx(colCur-1).build());
            }
        } else {
            if (rowCur>0 && !pieceRepo.existsByRowIdxAndColIdxAndChessGame(
                    rowCur-1, colCur, game
            )){
                list.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur).build());
                if (rowCur==6 && !pieceRepo.existsByRowIdxAndColIdxAndChessGame(
                        rowCur-2, colCur, game
                )) list.add(NewPosition.builder().rowIdx(rowCur-2).colIdx(colCur).build());
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        rowCur-1, colCur+1, !pawn.isWhite(), game
                )) list.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur+1).build());
                if (pieceRepo.existsByRowIdxAndColIdxAndIsWhiteAndChessGame(
                        rowCur-1, colCur-1, !pawn.isWhite(), game
                )) list.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur-1).build());
            }
        }
        return list;
    }
}
