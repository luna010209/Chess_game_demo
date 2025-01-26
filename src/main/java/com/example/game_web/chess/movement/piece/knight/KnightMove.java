package com.example.game_web.chess.movement.piece.knight;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KnightMove {
    private final ChessGameRepo gameRepo;
    private final ChessPieceRepo pieceRepo;

    public List<NewPosition> listMove(CurrentPosition knight){
        ChessGame game = gameRepo.findById(knight.getGameId()).orElseThrow(
                ()-> new CustomException("No exist game", HttpStatus.BAD_REQUEST)
        );
        List<NewPosition> listMove = new ArrayList<>();
        int rowCur = knight.getRowIdx();
        int colCur = knight.getColIdx();
        if (rowCur<7 && colCur<6 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                rowCur + 1, colCur + 2, knight.isWhite(), game)
        )
            listMove.add(NewPosition.builder().rowIdx(rowCur+1).colIdx(colCur+2).build());
        if (rowCur<7 && colCur>1 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                rowCur + 1, colCur - 2, knight.isWhite(), game
        ))
            listMove.add(NewPosition.builder().rowIdx(rowCur+1).colIdx(colCur-2).build());
        if (rowCur<6 && colCur<7 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                rowCur + 2, colCur + 1, knight.isWhite(), game
        ))
            listMove.add(NewPosition.builder().rowIdx(rowCur+2).colIdx(colCur+1).build());
        if (rowCur<6 && colCur>0 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                rowCur + 2, colCur - 1, knight.isWhite(), game
        ))
            listMove.add(NewPosition.builder().rowIdx(rowCur+2).colIdx(colCur-1).build());
        if (rowCur>0 && colCur<6 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                rowCur - 1, colCur + 2, knight.isWhite(), game
        ))
            listMove.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur+2).build());
        if (rowCur>0 && colCur>1 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                rowCur - 1, colCur - 2, knight.isWhite(), game
        ))
            listMove.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur-2).build());
        if (rowCur>1 && colCur<7 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                rowCur - 2, colCur + 1, knight.isWhite(), game
        ))
            listMove.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur-2).build());
        if (rowCur>1 && colCur>0 && !pieceRepo.existsByRowIdxAndColIdxAndWhiteAndChessGame(
                rowCur - 2, colCur - 1, knight.isWhite(), game
        ))
            listMove.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur-2).build());
        return listMove;
    }
}
