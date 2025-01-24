package com.example.game_web.chess.movement.piece.knight;

import com.example.game_web.chess.board.entity.ChessPiece;
import com.example.game_web.chess.board.repo.ChessPieceRepo;
import com.example.game_web.chess.movement.dto.CurrentPosition;
import com.example.game_web.chess.movement.dto.NewPosition;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KnightMove {
    private final ChessPieceRepo pieceRepo;
    public List<NewPosition> listMove(CurrentPosition current){
        List<NewPosition> listMove = new ArrayList<>();
        ChessPiece knight = pieceRepo.findByPieceAndIsWhite("knight", current.isWhite()).orElseThrow(
                ()-> new CustomException("Knight is not here", HttpStatus.BAD_REQUEST)
        );
        int rowCur = knight.getRowIdx();
        int colCur = knight.getColIdx();
        if (rowCur<7 && colCur<6 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowCur + 1, colCur + 2, knight.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowCur+1).colIdx(colCur+2).build());
        if (rowCur<7 && colCur>1 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowCur + 1, colCur - 2, knight.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowCur+1).colIdx(colCur-2).build());
        if (rowCur<6 && colCur<7 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowCur + 2, colCur + 1, knight.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowCur+2).colIdx(colCur+1).build());
        if (rowCur<6 && colCur>0 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowCur + 2, colCur - 1, knight.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowCur+2).colIdx(colCur-1).build());
        if (rowCur>0 && colCur<6 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowCur - 1, colCur + 2, knight.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur+2).build());
        if (rowCur>0 && colCur>1 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowCur - 1, colCur - 2, knight.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur-2).build());
        if (rowCur>1 && colCur<7 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowCur - 2, colCur + 1, knight.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur-2).build());
        if (rowCur>1 && colCur>0 && !pieceRepo.existsByRowIdxAndColIdxAndIsWhite(rowCur - 2, colCur - 1, knight.isWhite()))
            listMove.add(NewPosition.builder().rowIdx(rowCur-1).colIdx(colCur-2).build());
        return listMove;
    }
}
