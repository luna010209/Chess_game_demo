package com.example.game_web.chess.movement.piece.king;

import com.example.game_web.chess.board.entity.ChessGame;
import com.example.game_web.chess.board.entity.ChessPiece;
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
public class KingMove {
    private final ChessGameRepo gameRepo;
    private final ChessPieceRepo pieceRepo;

    // All possible movement of king.
    public List<NewPosition> listMove(CurrentPosition king){
        ChessGame game = gameRepo.findById(king.getGameId()).orElseThrow(
                ()-> new CustomException("No exist chess game", HttpStatus.BAD_REQUEST)
        );
//        List<ChessPiece> pieces = pieceRepo.findByWhiteAndChessGame(!king.isWhite(), game);
        List<NewPosition> list = new ArrayList<>();
        int rowCur = king.getRowIdx();
        int colCur = king.getColIdx();
        if (colCur < 7 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(rowCur, colCur + 1, king.isWhite(), game))
            list.add(NewPosition.builder().rowIdx(rowCur).colIdx(colCur + 1).build());
        if (colCur > 0 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(rowCur, colCur - 1, king.isWhite(), game))
            list.add(NewPosition.builder().rowIdx(rowCur).colIdx(colCur - 1).build());
        if (rowCur < 7 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(rowCur + 1, colCur, king.isWhite(), game))
            list.add(NewPosition.builder().rowIdx(rowCur + 1).colIdx(colCur).build());
        if (rowCur > 0 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(rowCur - 1, colCur, king.isWhite(), game))
            list.add(NewPosition.builder().rowIdx(rowCur - 1).colIdx(colCur).build());
        if (rowCur<7 && colCur < 7 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(rowCur + 1, colCur + 1, king.isWhite(), game))
            list.add(NewPosition.builder().rowIdx(rowCur+1).colIdx(colCur + 1).build());
        if (rowCur>0 && colCur < 7 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(rowCur-1, colCur + 1, king.isWhite(), game))
            list.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur + 1).build());
        if (rowCur<7 && colCur >0 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(rowCur+1, colCur - 1, king.isWhite(), game))
            list.add(NewPosition.builder().rowIdx(rowCur+1).colIdx(colCur - 1).build());
        if (rowCur>0 && colCur >0 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(rowCur-1, colCur - 1, king.isWhite(), game))
            list.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur - 1).build());
        return list;
    }

}
